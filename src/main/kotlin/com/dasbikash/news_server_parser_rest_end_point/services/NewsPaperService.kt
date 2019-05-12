package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.IllegalRequestBodyException
import com.dasbikash.news_server_parser_rest_end_point.model.NewsPaperStatusChangeRequest
import com.dasbikash.news_server_parser_rest_end_point.model.OffOnStatus
import com.dasbikash.news_server_parser_rest_end_point.model.database.GeneralLog
import com.dasbikash.news_server_parser_rest_end_point.model.database.Newspaper
import com.dasbikash.news_server_parser_rest_end_point.repositories.GeneralLogRepository
import com.dasbikash.news_server_parser_rest_end_point.repositories.NewspaperRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class NewsPaperService @Autowired
constructor(val newspaperRepository: NewspaperRepository,
            val authTokenService: AuthTokenService,
            val generalLogRepository: GeneralLogRepository){

    fun getAllActiveNewsPapers():List<Newspaper>{
        return newspaperRepository.findAllByActive()
    }

    fun requestNewspaperStatusChange(newsPaperStatusChangeRequest: NewsPaperStatusChangeRequest?)
            : NewsPaperStatusChangeRequest {
        if (newsPaperStatusChangeRequest == null ||
                newsPaperStatusChangeRequest.authToken==null ||
                newsPaperStatusChangeRequest.targetNewspaperId == null ||
                newsPaperStatusChangeRequest.targetStatus==null){
            throw IllegalRequestBodyException()
        }

        authTokenService.invalidateAuthToken(newsPaperStatusChangeRequest.authToken!!)

        val targetNewsPaperOptional =
                newspaperRepository.findById(newsPaperStatusChangeRequest.targetNewspaperId!!)

        if (!targetNewsPaperOptional.isPresent){
            throw IllegalRequestBodyException()
        }
        val targetNewsPaper = targetNewsPaperOptional.get()
        when(newsPaperStatusChangeRequest.targetStatus){
            OffOnStatus.ON -> targetNewsPaper.active = true
            OffOnStatus.OFF -> targetNewsPaper.active = false
        }
        newspaperRepository.save(targetNewsPaper)

        val logMessage = GeneralLog(created=Date())
        when(newsPaperStatusChangeRequest.targetStatus){
            OffOnStatus.ON -> {
                logMessage.logMessage = "Np: ${targetNewsPaper.name} activated"
            }
            OffOnStatus.OFF -> {
                logMessage.logMessage = "Np: ${targetNewsPaper.name} deactivated"
            }
        }
        generalLogRepository.save(logMessage)

        return newsPaperStatusChangeRequest
    }
}