package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.DataNotFoundException
import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.model.database.PageParsingHistory
import com.dasbikash.news_server_parser_rest_end_point.repositories.PageParsingHistoryRepository
import org.springframework.stereotype.Service

@Service
open class PageParsingHistoryService constructor(open var pageParsingHistoryRepository: PageParsingHistoryRepository)
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

    override fun getLogsAfterGivenId(lastLogId: Int, pageSize: Int): List<PageParsingHistory> {
        val lastPageParsingHistoryEntry = pageParsingHistoryRepository.findById(lastLogId)
        if (!lastPageParsingHistoryEntry.isPresent){
            throw DataNotFoundException()
        }
        return pageParsingHistoryRepository.getLogsAfterGivenId(lastPageParsingHistoryEntry.get().id!!,pageSize)
    }
    override fun delete(logEntry: PageParsingHistory){
        pageParsingHistoryRepository.delete(logEntry)
    }

    fun getLatestForPage(page: Page):PageParsingHistory?{
        return pageParsingHistoryRepository.getLatestForPage(page.id)
    }

    fun save(pageParsingHistory: PageParsingHistory):PageParsingHistory {
        return pageParsingHistoryRepository.save(pageParsingHistory)
    }

    fun getArticleCountForPageBetweenTwoDates(page: Page, startDateString: String, endDateString: String): Int {
        return pageParsingHistoryRepository.getArticleCountForPageBetweenTwoDates(page.id,startDateString,endDateString)
    }
}