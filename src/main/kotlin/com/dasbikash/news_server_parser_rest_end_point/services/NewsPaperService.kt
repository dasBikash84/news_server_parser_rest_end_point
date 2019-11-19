package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.IllegalRequestBodyException
import com.dasbikash.news_server_parser_rest_end_point.model.NewsPaperParserModeChangeRequest
import com.dasbikash.news_server_parser_rest_end_point.model.NewsPaperStatusChangeRequest
import com.dasbikash.news_server_parser_rest_end_point.model.OffOnStatus
import com.dasbikash.news_server_parser_rest_end_point.model.database.GeneralLog
import com.dasbikash.news_server_parser_rest_end_point.model.database.Newspaper
import com.dasbikash.news_server_parser_rest_end_point.model.database.NewspaperOpModeEntry
import com.dasbikash.news_server_parser_rest_end_point.model.database.ParserMode
import com.dasbikash.news_server_parser_rest_end_point.repositories.GeneralLogRepository
import com.dasbikash.news_server_parser_rest_end_point.repositories.NewspaperOpModeEntryRepository
import com.dasbikash.news_server_parser_rest_end_point.repositories.NewspaperRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
open class NewsPaperService
constructor(private var newspaperRepository: NewspaperRepository?=null,
            private var authTokenService: AuthTokenService?=null,
            private var generalLogRepository: GeneralLogRepository?=null,
            private var newspaperOpModeEntryRepository: NewspaperOpModeEntryRepository?=null) {

    fun getAllNewsPapers(): List<Newspaper> {
        return newspaperRepository!!.findAll().map {
            val allOpModeEntries = newspaperOpModeEntryRepository!!.findAllByNewspaper(it)
            if (allOpModeEntries.isEmpty()){
                it.setActive(false)
            }else {
                allOpModeEntries.sortedBy { it.created }.last().apply {
                    it.setActive((this.getOpMode() != ParserMode.OFF) && (this.getOpMode() != ParserMode.GET_SYNCED) )
                }
            }
            it
        }
    }

    fun getAllActiveNewsPapers(): List<Newspaper> {
        return getAllNewsPapers().filter { it.getActive() }
    }

    fun requestNewspaperStatusChange(newsPaperStatusChangeRequest: NewsPaperStatusChangeRequest?)
            : Newspaper {
        if (newsPaperStatusChangeRequest == null ||
                newsPaperStatusChangeRequest.authToken == null ||
                newsPaperStatusChangeRequest.targetNewspaperId == null ||
                newsPaperStatusChangeRequest.targetStatus == null) {
            throw IllegalRequestBodyException()
        }

        authTokenService!!.invalidateAuthToken(newsPaperStatusChangeRequest.authToken!!)

        val targetNewsPaperOptional =
                newspaperRepository!!.findById(newsPaperStatusChangeRequest.targetNewspaperId!!)

        if (!targetNewsPaperOptional.isPresent) {
            throw IllegalRequestBodyException()
        }
        val targetNewsPaper = targetNewsPaperOptional.get()
        when (newsPaperStatusChangeRequest.targetStatus) {
            OffOnStatus.ON -> targetNewsPaper.setActive(true)
            OffOnStatus.OFF -> targetNewsPaper.setActive(false)
        }
        newspaperRepository!!.save(targetNewsPaper)

        val logMessage = GeneralLog(created = Date())
        when (newsPaperStatusChangeRequest.targetStatus) {
            OffOnStatus.ON -> {
                logMessage.logMessage = "Np: ${targetNewsPaper.name} activated"
            }
            OffOnStatus.OFF -> {
                logMessage.logMessage = "Np: ${targetNewsPaper.name} deactivated"
            }
        }
        generalLogRepository!!.save(logMessage)

        return targetNewsPaper
    }

    fun requestNewspaperParserModeChange(newsPaperParserModeChangeRequest: NewsPaperParserModeChangeRequest?): NewspaperOpModeEntry {
        if (newsPaperParserModeChangeRequest == null ||
                newsPaperParserModeChangeRequest.authToken == null ||
                newsPaperParserModeChangeRequest.targetNewspaperId == null ||
                newsPaperParserModeChangeRequest.parserMode == null) {
            throw IllegalRequestBodyException()
        }

        authTokenService!!.invalidateAuthToken(newsPaperParserModeChangeRequest.authToken!!)

        val targetNewsPaperOptional =
                newspaperRepository!!.findById(newsPaperParserModeChangeRequest.targetNewspaperId!!)

        if (!targetNewsPaperOptional.isPresent) {
            throw IllegalRequestBodyException()
        }
        val targetNewsPaper = targetNewsPaperOptional.get()
        val newspaperOpModeEntry = NewspaperOpModeEntry(newspaper = targetNewsPaper, opMode = newsPaperParserModeChangeRequest.parserMode!!)
        newspaperOpModeEntryRepository!!.save(newspaperOpModeEntry)

        val logMessage = GeneralLog(created = Date(),
                logMessage = "Np: ${targetNewsPaper.name} parser mode set to " +
                        "${newsPaperParserModeChangeRequest.parserMode!!.name}")
        generalLogRepository!!.save(logMessage)
        return newspaperOpModeEntry
    }

    fun getNpCountForOpMode(parserMode: ParserMode):Int{
        return newspaperRepository!!
                .findAll()
                .asSequence()
                .filter {
                    val opModeList = newspaperOpModeEntryRepository!!
                            .findAllByNewspaper(it)
                            .sortedBy { it.created }
                    if (opModeList.isNotEmpty()) {
                        return@filter opModeList.last().getOpMode() == parserMode
                    }else{
                        newspaperOpModeEntryRepository!!.save(NewspaperOpModeEntry(newspaper=it))
                    }
                    false
                }
                .count()
    }

    fun getNpCountWithRunningOpMode():Int{
        return getNpCountForOpMode(ParserMode.RUNNING)
        /*return newspaperRepository!!
                .findAll()
                .asSequence()
                .filter {
                    val opModeList = newspaperOpModeEntryRepository!!
                            .findAllByNewspaper(it)
                            .sortedBy { it.created }
                    if (opModeList.isNotEmpty()) {
                        return@filter opModeList.last().getOpMode() == ParserMode.RUNNING
                    }
                    false
                }
                .count()*/
    }
    fun getNpCountWithGetSyncedOpMode():Int{
        return getNpCountForOpMode(ParserMode.GET_SYNCED)
        /*return newspaperRepository!!
                .findAll()
                .asSequence()
                .filter {
                    newspaperOpModeEntryRepository!!
                            .findAllByNewspaper(it)
                            .sortedBy { it.created }
                            .last().getOpMode()==ParserMode.GET_SYNCED
                }
                .count()*/
    }
    fun getNpCountWithParseThroughClientOpMode():Int{
        return getNpCountForOpMode(ParserMode.PARSE_THROUGH_CLIENT)
        /*return newspaperRepository!!
                .findAll()
                .asSequence()
                .filter {
                    newspaperOpModeEntryRepository!!
                            .findAllByNewspaper(it)
                            .sortedBy { it.created }
                            .last().getOpMode()==ParserMode.PARSE_THROUGH_CLIENT
                }
                .count()*/
    }

    fun getCount(): Long {
        return newspaperRepository!!.count()
    }

    fun save(newspaper: Newspaper):Newspaper {
        return newspaperRepository!!.save(newspaper)
    }

    fun saveAll(newspapers: Collection<Newspaper>):List<Newspaper> {
        return newspaperRepository!!.saveAll(newspapers)
    }

    fun getLatestOpModeEntryForNewspaper(newspaper: Newspaper):NewspaperOpModeEntry{
        newspaperOpModeEntryRepository!!
                .getAllByNewspaperOrderByCreatedDesc(newspaper).apply {
                    if (isNotEmpty()){
                        return get(0)
                    }
                }
        return newspaperOpModeEntryRepository!!.save(NewspaperOpModeEntry(newspaper=newspaper))
    }
}