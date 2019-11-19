package com.dasbikash.news_server_parser_rest_end_point.model

import com.dasbikash.news_server_parser_rest_end_point.model.database.NsParserRestDbEntity
import com.dasbikash.news_server_parser_rest_end_point.model.database.ParserMode
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class NewsPaperParserModeChangeRequest(
        var authToken: String? = null,
        var targetNewspaperId: String? = null,
        var parserMode: ParserMode? = null
): NsParserRestDbEntity{

    fun isRunningRequest():Boolean{
        return parserMode?.equals(ParserMode.RUNNING) ?: false
    }
    fun isGetSyncedRequest():Boolean{
        return parserMode?.equals(ParserMode.GET_SYNCED) ?: false
    }
    fun isParseThroughClientRequest():Boolean{
        return parserMode?.equals(ParserMode.PARSE_THROUGH_CLIENT) ?: false
    }
    fun isOffRequest():Boolean{
        return parserMode?.equals(ParserMode.OFF) ?: false
    }
    fun hasValidMode():Boolean{
        return parserMode?.equals(ParserMode.OFF) ?: false ||
                parserMode?.equals(ParserMode.GET_SYNCED) ?: false ||
                parserMode?.equals(ParserMode.PARSE_THROUGH_CLIENT) ?: false ||
                parserMode?.equals(ParserMode.RUNNING) ?: false
    }

}
@XmlRootElement
class NewsPaperParserModeChangeRequestFormat(
        var authToken: String = "Emailed token",
        var targetNewspaperId: String = "targetNewspaperId",
        var parserMode: String = ParserMode.values().joinToString(separator = " | ")
): NsParserRestDbEntity