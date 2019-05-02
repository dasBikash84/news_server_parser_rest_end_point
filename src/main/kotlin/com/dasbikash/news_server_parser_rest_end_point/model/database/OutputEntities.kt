package com.dasbikash.news_server_parser_rest_end_point.model.database

class Languages(
        val languages:List<Language>
):NsParserRestDbEntity
class Countries(
        val countries:List<Country>
):NsParserRestDbEntity
class Newspapers(
        val Newspapers:List<Newspaper>
):NsParserRestDbEntity
class Pages(
        val pages:List<Page>
):NsParserRestDbEntity
class Articles(
        val articles:List<Article>
):NsParserRestDbEntity