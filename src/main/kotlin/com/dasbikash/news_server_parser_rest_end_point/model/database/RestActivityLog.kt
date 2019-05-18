package com.dasbikash.news_server_parser_rest_end_point.model.database

import org.aspectj.lang.JoinPoint
import javax.persistence.*
import javax.servlet.http.HttpServletRequest

@Entity
@Table(name = DatabaseTableNames.REST_ACTIVITY_LOG_TABLE_NAME)
class RestActivityLog(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id:Int?=null,
        val requestURL:String,
        val requestMethod:String,
        val remoteHost:String,
        val methodSignature:String,
        val timeTakenMs:Int,
        val returnedEntiryCount:Int?=null,
        val exceptionClassName:String?=null
){
    companion object{
        fun getInstance(joinPoint: JoinPoint, request: HttpServletRequest, timeTakenMs: Int,
                        exceptionClassFullName: String?=null, returnedEntiryCount:Int?=null)
                :RestActivityLog{
            return RestActivityLog(requestURL = request.requestURL.toString(),methodSignature = joinPoint.signature.toString(),
                    requestMethod = request.method,remoteHost = request.remoteHost,timeTakenMs = timeTakenMs,
                    exceptionClassName = exceptionClassFullName,returnedEntiryCount = returnedEntiryCount)
        }
    }
}