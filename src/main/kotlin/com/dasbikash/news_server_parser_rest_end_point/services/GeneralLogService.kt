package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.DataNotFoundException
import com.dasbikash.news_server_parser_rest_end_point.model.database.GeneralLog
import com.dasbikash.news_server_parser_rest_end_point.repositories.GeneralLogRepository
import org.springframework.stereotype.Service

@Service
open class GeneralLogService
constructor(open var generalLogRepository: GeneralLogRepository):DeletableLogService<GeneralLog>{
    fun getLatestGeneralLogs(pageSize: Int): List<GeneralLog> {
        return generalLogRepository.getLatestGeneralLogs(pageSize)
    }

    fun getGeneralLogsBeforeGivenId(lastGeneralLogId: Int, pageSize: Int): List<GeneralLog> {
        val lastGeneralLog = generalLogRepository.findById(lastGeneralLogId)
        if (!lastGeneralLog.isPresent){
            throw DataNotFoundException()
        }
        return generalLogRepository.getSettingsUpdateLogsBeforeGivenId(lastGeneralLog.get().id!!,pageSize)
    }
    override fun getOldestLogs(pageSize: Int): List<GeneralLog> {
        return generalLogRepository.getOldestLogs(pageSize)
    }

    override fun getLogsAfterGivenId(lastLogId: Int, pageSize: Int): List<GeneralLog> {
        val lastGeneralLog = generalLogRepository.findById(lastLogId)
        if (!lastGeneralLog.isPresent){
            throw DataNotFoundException()
        }
        return generalLogRepository.getLogsAfterGivenId(lastGeneralLog.get().id!!,pageSize)
    }
    override fun delete(logEntry: GeneralLog){
        generalLogRepository.delete(logEntry)
    }
}