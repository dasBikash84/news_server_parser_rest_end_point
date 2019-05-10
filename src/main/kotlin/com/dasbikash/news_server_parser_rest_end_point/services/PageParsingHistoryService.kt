package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.DataNotFoundException
import com.dasbikash.news_server_parser_rest_end_point.model.database.PageParsingHistory
import com.dasbikash.news_server_parser_rest_end_point.repositories.PageParsingHistoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PageParsingHistoryService @Autowired constructor(val pageParsingHistoryRepository: PageParsingHistoryRepository)
    :DeletableLogService<PageParsingHistory>{

    fun getLatestPageParsingHistories(pageSize: Int): List<PageParsingHistory> {
        return pageParsingHistoryRepository.getLatestPageParsingHistories(pageSize)
    }

    fun getPageParsingHistoriesBeforeGivenId(lastPageParsingHistoryId: Int, pageSize: Int): List<PageParsingHistory> {
        val lastPageParsingHistoryEntry = pageParsingHistoryRepository.findById(lastPageParsingHistoryId)
        if (!lastPageParsingHistoryEntry.isPresent){
            throw DataNotFoundException()
        }
        return pageParsingHistoryRepository.getPageParsingHistoriesBeforeGivenId(lastPageParsingHistoryEntry.get().id!!,pageSize)
    }

    override fun getOldestLogs(pageSize: Int): List<PageParsingHistory> {
        return pageParsingHistoryRepository.getOldestLogs(pageSize)
    }

    override fun getLogsAfterGivenId(lastPageParsingHistoryId: Int, pageSize: Int): List<PageParsingHistory> {
        val lastPageParsingHistoryEntry = pageParsingHistoryRepository.findById(lastPageParsingHistoryId)
        if (!lastPageParsingHistoryEntry.isPresent){
            throw DataNotFoundException()
        }
        return pageParsingHistoryRepository.getLogsAfterGivenId(lastPageParsingHistoryEntry.get().id!!,pageSize)
    }
    override fun delete(errorLog: PageParsingHistory){
        pageParsingHistoryRepository.delete(errorLog)
    }
}