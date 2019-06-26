package com.dasbikash.news_server_parser_rest_end_point.model.database

enum class ExceptionClassNames {
    ParserStoppedException,
    ParserRestartedException,
    ParserNotFoundException,
    ParserException,
    PageLinkGenerationException,
    NewsPaperNotFoundForPageException,
    EmptyJsoupDocumentException,
    EmptyArticlePreviewPageException,
    EmptyArticleLinkException,
    EmptyArticleBodyException,
    ArticleModificationTimeNotFoundException,
    ReportGenerationException,
    ParserExitException
}