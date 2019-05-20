package com.dasbikash.news_server_parser_rest_end_point.model

import com.dasbikash.news_server_parser_rest_end_point.model.database.*
import com.fasterxml.jackson.annotation.JsonProperty
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class Languages(var languages: List<Language>?=null) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return languages?.size ?: 0
    }
}
@XmlRootElement
class Countries(var countries: List<Country>?=null) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return countries?.size ?: 0
    }
}
@XmlRootElement
class Newspapers(var newspapers: List<Newspaper>?=null) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return newspapers?.size ?: 0
    }
}
@XmlRootElement
class Pages(var pages: List<Page>?=null) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return pages?.size ?: 0
    }
}
@XmlRootElement
class Articles(articles: List<Article>?=null) : NsParserRestDbEntity, OutputWrapper {
    private var articles: List<Article>?=null

    init {
        articles?.asSequence()?.forEach { it.articleText = it.articleText?.trim() }
        this.articles = articles
    }

    fun setArticles(articles: List<Article>?){
        articles?.asSequence()?.forEach { it.articleText = it.articleText?.trim() }
        this.articles = articles
    }
    @JsonProperty
    @XmlElement
    fun getArticles():List<Article>?{
        return articles
    }

    override fun getOutPutCount(): Int {
        return articles?.size ?: 0
    }
}
@XmlRootElement
class PageGroups(var pageGroups: List<PageGroup>?=null) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return pageGroups?.size ?: 0
    }
}
@XmlRootElement
class GeneralLogs(
        var generalLogs: List<GeneralLog>?=null
) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return generalLogs?.size ?: 0
    }
}
@XmlRootElement
class ErrorLogs(
        var errorLogs: List<ErrorLog>?=null
) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return errorLogs?.size ?: 0
    }
}
@XmlRootElement
class PageParsingHistories(
        var pageParsingHistories: List<PageParsingHistory>?=null
) : NsParserRestDbEntity, OutputWrapper {
    override fun getOutPutCount(): Int {
        return pageParsingHistories?.size ?: 0
    }
}

interface OutputWrapper {
    fun getOutPutCount(): Int
}