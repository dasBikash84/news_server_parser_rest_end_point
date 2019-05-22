package com.dasbikash.news_server_parser_rest_end_point.container_filters

import com.dasbikash.news_server_parser_rest_end_point.model.OutputWrapper
import com.dasbikash.news_server_parser_rest_end_point.model.database.RestActivityLog
import com.dasbikash.news_server_parser_rest_end_point.repositories.RestActivityLogRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.springframework.stereotype.Component
import java.io.IOException
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.ext.Provider


@Component
@Provider
open class LoggingFilter(open var restActivityLogRepository: RestActivityLogRepository)
    : ContainerRequestFilter, ContainerResponseFilter {

    private val REQUEST_START_TIME_PROPERTY_TAG = "start_time"

    @Throws(IOException::class)
    override fun filter(requestContext: ContainerRequestContext) {
        val startingTime = System.currentTimeMillis()
        requestContext.setProperty(REQUEST_START_TIME_PROPERTY_TAG,startingTime)
    }


    @Throws(IOException::class)
    override fun filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext) {
        val currentTime = System.currentTimeMillis()
        val requestURL = requestContext.uriInfo.requestUri.toURL().toString()
        val requestMethod = requestContext.request.method
        val remoteHost = ""
        val methodSignature = ""
        var timeTakenMs = 0
        try {
            timeTakenMs = (currentTime - requestContext.getProperty(REQUEST_START_TIME_PROPERTY_TAG) as Long).toInt()
        }catch (ex:Exception){}
        var exceptionClassName: String? = null
        var returnedEntiryCount: Int? = null
        val acceptHeader = requestContext.headers.get(RestActivityLog.REQUEST_ACCEPT_HEADER_NAME)?.toString()
        val userAgentHeader = requestContext.headers.get(RestActivityLog.REQUEST_USER_AGENT_HEADER_NAME)?.toString()

        if (responseContext.hasEntity()){
            when(responseContext.entity) {
                is String -> exceptionClassName = responseContext.entity.toString()
                is OutputWrapper -> returnedEntiryCount = (responseContext.entity as OutputWrapper).getOutPutCount()
            }
        }
        val restActivityLog = RestActivityLog(requestURL = requestURL,requestMethod = requestMethod,
                                                remoteHost = remoteHost,methodSignature = methodSignature,
                                                timeTakenMs = timeTakenMs,returnedEntiryCount = returnedEntiryCount,
                                                exceptionClassName = exceptionClassName,acceptHeader = acceptHeader,
                                                userAgentHeader = userAgentHeader)
        println("From ${this::class.java.simpleName}: ${restActivityLog}")
//        Observable.just(restActivityLog)
//                .subscribeOn(Schedulers.io())
//                .map {
//                    restActivityLogRepository.save(it)
//                }
//                .subscribe()
    }

}