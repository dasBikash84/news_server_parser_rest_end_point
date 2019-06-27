package com.dasbikash.news_server_parser_rest_end_point.model

import com.dasbikash.news_server_parser_rest_end_point.model.database.NsParserRestDbEntity
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class NewsPaperStatusChangeRequest(
        var authToken: String? = null,
        var targetNewspaperId: String? = null,
        var targetStatus: OffOnStatus? = null
): NsParserRestDbEntity
@XmlRootElement
class NewsPaperStatusChangeRequestFormat(
        var authToken: String = "Emailed token",
        var targetNewspaperId: String = "targetNewspaperId",
        var targetStatus: String = OffOnStatus.values().joinToString(separator = " | ")
): NsParserRestDbEntity