package com.dasbikash.news_server_parser_rest_end_point.repositories

import com.dasbikash.news_server_parser_rest_end_point.model.database.DatabaseTableNames
import com.dasbikash.news_server_parser_rest_end_point.model.database.GeneralLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GeneralLogRepository : JpaRepository<GeneralLog, Int>,DeletableLogRepository<GeneralLog>{
    @Query(value = "SELECT * FROM ${DatabaseTableNames.GENERAL_LOG_TABLE_NAME} order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getLatestGeneralLogs(pageSize: Int): List<GeneralLog>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.GENERAL_LOG_TABLE_NAME} WHERE id < :lastGeneralLogId order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getSettingsUpdateLogsBeforeGivenId(lastGeneralLogId: Int, pageSize: Int): List<GeneralLog>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.GENERAL_LOG_TABLE_NAME} order by id ASC limit :pageSize",
            nativeQuery = true)
    override fun getOldestLogs(pageSize: Int): List<GeneralLog>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.GENERAL_LOG_TABLE_NAME} WHERE id >= :lastLogId order by id ASC limit :pageSize",
            nativeQuery = true)
    override fun getLogsAfterGivenId(lastLogId: Int, pageSize: Int): List<GeneralLog>
}