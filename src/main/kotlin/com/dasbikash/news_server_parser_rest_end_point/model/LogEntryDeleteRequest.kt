package com.dasbikash.news_server_parser_rest_end_point.model

import com.dasbikash.news_server_parser_rest_end_point.model.database.NsParserRestDbEntity

class LogEntryDeleteRequest(
        val authToken: String? = null,
        val targetLogId: Int? = null,
        var entryDeleteCount: Int? = null
) : NsParserRestDbEntity {
    companion object {
        const val MAX_ENTRY_DELETE_LIMIT = 50
        const val DEFAULT_ENTRY_DELETE_COUNT = 10
    }
}

class LogEntryDeleteRequestFormat (
        val authToken:String = "Emailed token",
        val targetLogId: String = "Log id for entry deletion from specific location. " +
                                    "Oldest entry will be deleted for null",
        val entryDeleteCount: String = "Log enrty delete count. " +
                                        "Max:${LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT}. "+
                                        "Default:${LogEntryDeleteRequest.DEFAULT_ENTRY_DELETE_COUNT}"
): NsParserRestDbEntity