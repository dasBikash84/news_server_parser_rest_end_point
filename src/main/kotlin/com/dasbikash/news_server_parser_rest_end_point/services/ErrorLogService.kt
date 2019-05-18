package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.DataNotFoundException
import com.dasbikash.news_server_parser_rest_end_point.model.database.ErrorLog
import com.dasbikash.news_server_parser_rest_end_point.repositories.ErrorLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class ErrorLogService
constructor(open var errorLogRepository: ErrorLogRepository)
    :DeletableLogService<ErrorLog>{
    fun getLatestErrorLogs(pageSize: Int): List<ErrorLog> {
        return errorLogRepository.getLatestErrorLogs(pageSize)
    }

    fun getErrorLogsBeforeGivenId(lastErrorLogId: Int, pageSize: Int): List<ErrorLog> {
        val lastErrorLog = errorLogRepository.findById(lastErrorLogId)
        if (!lastErrorLog.isPresent){
            throw DataNotFoundException()
        }
        return errorLogRepository.getErrorLogsBeforeGivenId(lastErrorLog.get().id!!,pageSize)
    }

    override fun getOldestLogs(pageSize: Int): List<ErrorLog> {
        return errorLogRepository.getOldestLogs(pageSize)
    }

    override fun getLogsAfterGivenId(lastErrorLogId: Int, pageSize: Int): List<ErrorLog> {
        val lastErrorLog = errorLogRepository.findById(lastErrorLogId)
        if (!lastErrorLog.isPresent){
            throw DataNotFoundException()
        }
        return errorLogRepository.getLogsAfterGivenId(lastErrorLog.get().id!!,pageSize)
    }
    override fun delete(errorLog: ErrorLog){
        errorLogRepository.delete(errorLog)
    }
}