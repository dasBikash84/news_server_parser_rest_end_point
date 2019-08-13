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
constructor(open var newspaperRepository: NewspaperRepository,
            open var authTokenService: AuthTokenService,
            open var generalLogRepository: GeneralLogRepository,
            open var newspaperOpModeEntryRepository: NewspaperOpModeEntryRepository) {

    fun getAllNewsPapers(): List<Newspaper> {
        return newspaperRepository.findAll().map {
            val allOpModeEntries = newspaperOpModeEntryRepository.findAllByNewspaper(it)
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

        authTokenService.invalidateAuthToken(newsPaperStatusChangeRequest.authToken!!)

        val targetNewsPaperOptional =
                newspaperRepository.findById(newsPaperStatusChangeRequest.targetNewspaperId!!)

        if (!targetNewsPaperOptional.isPresent) {
            throw IllegalRequestBodyException()
        }
        val targetNewsPaper = targetNewsPaperOptional.get()
        when (newsPaperStatusChangeRequest.targetStatus) {
            OffOnStatus.ON -> targetNewsPaper.setActive(true)
            OffOnStatus.OFF -> targetNewsPaper.setActive(false)
        }
        newspaperRepository.save(targetNewsPaper)

        val logMessage = GeneralLog(created = Date())
        when (newsPaperStatusChangeRequest.targetStatus) {
            OffOnStatus.ON -> {
                logMessage.logMessage = "Np: ${targetNewsPaper.name} activated"
            }
            OffOnStatus.OFF -> {
                logMessage.logMessage = "Np: ${targetNewsPaper.name} deactivated"
            }
        }
        generalLogRepository.save(logMessage)

        return targetNewsPaper
    }

    fun requestNewspaperParserModeChange(newsPaperParserModeChangeRequest: NewsPaperParserModeChangeRequest?): NewspaperOpModeEntry {
        if (newsPaperParserModeChangeRequest == null ||
                newsPaperParserModeChangeRequest.authToken == null ||
                newsPaperParserModeChangeRequest.targetNewspaperId == null ||
                newsPaperParserModeChangeRequest.parserMode == null) {
            throw IllegalRequestBodyException()
        }

        authTokenService.invalidateAuthToken(newsPaperParserModeChangeRequest.authToken!!)

        val targetNewsPaperOptional =
                newspaperRepository.findById(newsPaperParserModeChangeRequest.targetNewspaperId!!)

        if (!targetNewsPaperOptional.isPresent) {
            throw IllegalRequestBodyException()
        }
        val targetNewsPaper = targetNewsPaperOptional.get()
        val newspaperOpModeEntry = NewspaperOpModeEntry(newspaper = targetNewsPaper, opMode = newsPaperParserModeChangeRequest.parserMode!!)
        newspaperOpModeEntryRepository.save(newspaperOpModeEntry)

        val logMessage = GeneralLog(created = Date(),
                logMessage = "Np: ${targetNewsPaper.name} parser mode set to " +
                        "${newsPaperParserModeChangeRequest.parserMode!!.name}")
        generalLogRepository.save(logMessage)
        return newspaperOpModeEntry
    }
}