package com.dasbikash.news_server_parser_rest_end_point.model

import com.dasbikash.news_server_parser_rest_end_point.model.database.NsParserRestDbEntity
import com.dasbikash.news_server_parser_rest_end_point.model.database.ParserMode
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class NewsPaperParserModeChangeRequest(
        var authToken: String? = null,
        var targetNewspaperId: String? = null,
        var parserMode: ParserMode? = null
): NsParserRestDbEntity
@XmlRootElement
class NewsPaperParserModeChangeRequestFormat(
        var authToken: String = "Emailed token",
        var targetNewspaperId: String = "targetNewspaperId",
        var parserMode: String = ParserMode.values().joinToString(separator = " | ")
): NsParserRestDbEntity