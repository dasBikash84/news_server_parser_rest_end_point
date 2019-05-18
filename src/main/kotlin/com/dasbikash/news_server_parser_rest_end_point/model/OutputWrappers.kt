package com.dasbikash.news_server_parser_rest_end_point.model

import com.dasbikash.news_server_parser_rest_end_point.model.database.*

class Languages(val languages: List<Language>) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return languages.size
    }
}

class Countries(val countries: List<Country>) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return countries.size
    }
}

class Newspapers(val newspapers: List<Newspaper>) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return newspapers.size
    }
}

class Pages(val pages: List<Page>) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return pages.size
    }
}

class Articles(articles: List<Article>) : NsParserRestDbEntity, OutputWrapper {
    val articles: List<Article>

    init {
        articles.asSequence().forEach { it.articleText = it.articleText?.trim() }
        this.articles = articles
    }

    override fun getOutPutCount(): Int {
        return articles.size
    }
}

class PageGroups(val pageGroups: List<PageGroup>) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return pageGroups.size
    }
}

class GeneralLogs(
        val generalLogs: List<GeneralLog>
) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return generalLogs.size
    }
}

class ErrorLogs(
        val errorLogs: List<ErrorLog>
) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return errorLogs.size
    }
}

class PageParsingHistories(
        val pageParsingHistories: List<PageParsingHistory>
) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return pageParsingHistories.size
    }
}

interface OutputWrapper {
    fun getOutPutCount(): Int
}