package com.dasbikash.news_server_parser_rest_end_point.utills

import com.dasbikash.news_server_parser_rest_end_point.exceptions.DataNotFoundException
import com.dasbikash.news_server_parser_rest_end_point.exceptions.IllegalRequestBodyException
import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequest
import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_parser_rest_end_point.model.NewsPaperStatusChangeRequestFormat
import com.dasbikash.news_server_parser_rest_end_point.model.database.NsParserRestDbEntity
import com.dasbikash.news_server_parser_rest_end_point.services.AuthTokenService
import com.dasbikash.news_server_parser_rest_end_point.services.DeletableLogService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.ws.rs.core.Response

@Service
open class RestControllerUtils
constructor(open var authTokenService: AuthTokenService){
    fun <T : NsParserRestDbEntity> listEntityToResponseEntity(entiryList: List<T>): Response {
        if (entiryList.isEmpty()) {
            throw DataNotFoundException()
        }
        return Response.status(Response.Status.OK).entity(entiryList).build()
    }

    fun <T : NsParserRestDbEntity> entityToResponseEntity(entity: T?): Response {
        if (entity == null) {
            throw DataNotFoundException()
        }
        return Response.status(Response.Status.OK).entity(entity).build()
    }

    private fun <T> generateAndEmailNewAuthtoken(type: Class<T>){
        val newToken = authTokenService.getNewAuthToken()
        EmailUtils.emailAuthTokenToAdmin(newToken, type)
    }

    fun <T> generateLogDeleteToken(type: Class<T>): Response {
        generateAndEmailNewAuthtoken(type)
        return entityToResponseEntity(LogEntryDeleteRequestFormat())
    }

    fun <T> generateNewspaperStatusChangeToken(type: Class<T>): Response {
        generateAndEmailNewAuthtoken(type)
        return entityToResponseEntity(NewsPaperStatusChangeRequestFormat())
    }

    fun validateLogEntryDeleteRequest(logEntryDeleteRequest: LogEntryDeleteRequest?) {
        if (logEntryDeleteRequest == null ||
                logEntryDeleteRequest.authToken == null
        ) {
            throw IllegalRequestBodyException()
        }
    }

    fun <T : NsParserRestDbEntity> deleteLogEntries(deletableLogService: DeletableLogService<T>,
                                                    logEntryDeleteRequest: LogEntryDeleteRequest?)
            : List<T> {

        validateLogEntryDeleteRequest(logEntryDeleteRequest)
        authTokenService.invalidateAuthToken(logEntryDeleteRequest!!.authToken!!)

        val logEntriesForDeletion = mutableListOf<T>()

        if (logEntryDeleteRequest.targetLogId == null) {
            if (logEntryDeleteRequest.entryDeleteCount == null ||
                    logEntryDeleteRequest.entryDeleteCount!! < 0) {
                logEntriesForDeletion.addAll(deletableLogService.getOldestLogs(LogEntryDeleteRequest.DEFAULT_ENTRY_DELETE_COUNT))
            } else {
                if (logEntryDeleteRequest.entryDeleteCount!! > LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT) {
                    logEntriesForDeletion.addAll(deletableLogService
                            .getOldestLogs(LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT))
                } else {
                    logEntriesForDeletion.addAll(deletableLogService
                            .getOldestLogs(logEntryDeleteRequest.entryDeleteCount!!))
                }
            }
        } else {
            if (logEntryDeleteRequest.entryDeleteCount == null ||
                    logEntryDeleteRequest.entryDeleteCount!! < 0) {
                logEntriesForDeletion.addAll(deletableLogService.getLogsAfterGivenId(logEntryDeleteRequest.targetLogId!!, LogEntryDeleteRequest.DEFAULT_ENTRY_DELETE_COUNT))
            } else {
                if (logEntryDeleteRequest.entryDeleteCount!! > LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT) {
                    logEntriesForDeletion.addAll(deletableLogService
                            .getLogsAfterGivenId(logEntryDeleteRequest.targetLogId!!, LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT))
                } else {
                    logEntriesForDeletion.addAll(deletableLogService
                            .getLogsAfterGivenId(logEntryDeleteRequest.targetLogId!!, logEntryDeleteRequest.entryDeleteCount!!))
                }
            }
        }
        logEntriesForDeletion.asSequence().forEach {
            deletableLogService.delete(it)
        }
        return logEntriesForDeletion
    }
}