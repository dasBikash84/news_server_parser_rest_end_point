package com.dasbikash.news_server_parser_rest_end_point.exceptions

open class EmailSendingException : InternalError {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}