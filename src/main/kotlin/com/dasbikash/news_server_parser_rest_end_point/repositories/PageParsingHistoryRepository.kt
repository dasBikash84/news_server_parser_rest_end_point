package com.dasbikash.news_server_parser_rest_end_point.repositories

import com.dasbikash.news_server_parser_rest_end_point.model.database.DatabaseTableNames
import com.dasbikash.news_server_parser_rest_end_point.model.database.PageParsingHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PageParsingHistoryRepository : JpaRepository<PageParsingHistory, Int>,DeletableLogRepository<PageParsingHistory>{

    @Query(value = "SELECT * FROM ${DatabaseTableNames.PAGE_PARSING_HISTORY_TABLE_NAME} order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getLatestPageParsingHistories(pageSize: Int): List<PageParsingHistory>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.PAGE_PARSING_HISTORY_TABLE_NAME} WHERE id < :lastPageParsingHistoryId order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getPageParsingHistoriesBeforeGivenId(lastPageParsingHistoryId: Int, pageSize: Int): List<PageParsingHistory>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.PAGE_PARSING_HISTORY_TABLE_NAME} order by id ASC limit :pageSize",
            nativeQuery = true)
    override fun getOldestLogs(pageSize: Int): List<PageParsingHistory>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.PAGE_PARSING_HISTORY_TABLE_NAME} WHERE id >= :lastPageParsingHistoryId order by id ASC limit :pageSize",
            nativeQuery = true)
    override fun getLogsAfterGivenId(lastPageParsingHistoryId: Int, pageSize: Int): List<PageParsingHistory>
}