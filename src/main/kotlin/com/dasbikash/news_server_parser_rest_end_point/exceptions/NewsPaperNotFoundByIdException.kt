package com.dasbikash.news_server_parser_rest_end_point.exceptions

class NewsPaperNotFoundByIdException : DataNotFoundException {
    constructor(newspaperId: String) : super("Newspaper with id: ${newspaperId} was not found.")
}