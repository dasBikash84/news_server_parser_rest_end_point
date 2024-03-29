package com.dasbikash.news_server_parser_rest_end_point.model

import com.dasbikash.news_server_parser_rest_end_point.model.database.RestActivityLog
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.UriInfo

class RequestDetailsBean(
    @Context private val request: HttpServletRequest,
    @Context private val httpheaders: HttpHeaders,
    @Context private val uriInfo: UriInfo
){
    val requestURL: String
        get() = uriInfo.requestUri.toString()

    val acceptHeader: String?
        get() = httpheaders.requestHeaders.get(RestActivityLog.REQUEST_ACCEPT_HEADER_NAME)?.toString()

    val userAgentHeader: String?
        get() = httpheaders.requestHeaders.get(RestActivityLog.REQUEST_USER_AGENT_HEADER_NAME)?.toString()

    val requestMethod: String
        get() = request.method

    val remoteHost: String
        get() = request.remoteAddr

}