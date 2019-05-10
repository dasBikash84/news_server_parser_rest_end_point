package com.dasbikash.news_server_parser_rest_end_point.model

data class EmailAuth (
    var userName:String? = null,
    var passWord:String? = null,
    var properties:Map<String,String>?=null
)