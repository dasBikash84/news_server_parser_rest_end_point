package com.dasbikash.news_server_parser_rest_end_point.repositories

import com.dasbikash.news_server_parser_rest_end_point.model.database.NsParserRestDbEntity

interface DeletableLogRepository<T: NsParserRestDbEntity> {
    fun getOldestLogs(pageSize: Int): List<T>
    fun getLogsAfterGivenId(lastLogId: Int, pageSize: Int): List<T>
}