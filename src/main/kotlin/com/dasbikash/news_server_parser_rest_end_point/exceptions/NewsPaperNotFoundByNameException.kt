package com.dasbikash.news_server_parser_rest_end_point.exceptions

class NewsPaperNotFoundByNameException(newsPaperName: String) :
        DataNotFoundException("Newspaper with name: ${newsPaperName} was not found.")
