package com.dasbikash.news_server_parser_rest_end_point.model

import com.dasbikash.news_server_parser_rest_end_point.model.database.NsParserRestDbEntity

class NewsPaperStatusChangeRequest(
        var authToken: String? = null,
        var targetNewspaperId: String? = null,
        var targetStatus: OffOnStatus? = null
): NsParserRestDbEntity

class NewsPaperStatusChangeRequestFormat(
        val authToken: String = "Emailed token",
        val targetNewspaperId: String = "targetNewspaperId",
        val targetStatus: String = "ON/OFF"
): NsParserRestDbEntity