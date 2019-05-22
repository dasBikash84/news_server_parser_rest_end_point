package com.dasbikash.news_server_parser_rest_end_point.model.database

import com.dasbikash.news_server_parser_rest_end_point.model.RequestDetailsBean
import org.aspectj.lang.JoinPoint
import javax.persistence.*

@Entity
@Table(name = DatabaseTableNames.REST_ACTIVITY_LOG_TABLE_NAME)
data class RestActivityLog(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        val requestURL: String,
        val requestMethod: String,
        val remoteHost: String,
        val methodSignature: String,
        val timeTakenMs: Int,
        val returnedEntiryCount: Int? = null,
        val exceptionClassName: String? = null,
        val acceptHeader: String? = null,
        val userAgentHeader: String? = null
) {
    companion object {

        const val REQUEST_ACCEPT_HEADER_NAME = "accept"
        const val REQUEST_USER_AGENT_HEADER_NAME = "user-agent"

        fun getInstance(joinPoint: JoinPoint, timeTakenMs: Int,
                        exceptionClassFullName: String? = null, returnedEntiryCount: Int? = null,
                        requestDetails: RequestDetailsBean)
                : RestActivityLog {
            return RestActivityLog(requestURL = requestDetails.requestURL, methodSignature = joinPoint.signature.toString(),
                    requestMethod = requestDetails.requestMethod, remoteHost = requestDetails.remoteHost, timeTakenMs = timeTakenMs,
                    exceptionClassName = exceptionClassFullName, returnedEntiryCount = returnedEntiryCount,
                    acceptHeader = requestDetails.acceptHeader, userAgentHeader = requestDetails.userAgentHeader)
        }
    }
}