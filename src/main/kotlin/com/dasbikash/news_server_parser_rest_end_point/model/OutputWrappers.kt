package com.dasbikash.news_server_parser_rest_end_point.model

import com.dasbikash.news_server_parser_rest_end_point.model.database.*

class Languages(val languages:List<Language>): NsParserRestDbEntity
class Countries(val countries:List<Country>): NsParserRestDbEntity
class Newspapers(val Newspapers:List<Newspaper>): NsParserRestDbEntity
class Pages(val pages:List<Page>): NsParserRestDbEntity
class Articles(articles:List<Article>): NsParserRestDbEntity {
    val articles:List<Article>
    init {
        articles.asSequence().forEach {it.articleText = it.articleText?.trim()}
        this.articles = articles
    }
}
class PageGroups(val pageGroups:List<PageGroup>): NsParserRestDbEntity
class GeneralLogs(
        val generalLogs:List<GeneralLog>
): NsParserRestDbEntity