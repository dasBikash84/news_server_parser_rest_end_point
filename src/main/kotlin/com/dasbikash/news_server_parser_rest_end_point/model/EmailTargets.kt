package com.dasbikash.news_server_parser_rest_end_point.model

data class EmailTargets (
    var toAddresses:String? = null,
    var ccAddresses:String? = null,
    var bccAddresses:String? = null
)