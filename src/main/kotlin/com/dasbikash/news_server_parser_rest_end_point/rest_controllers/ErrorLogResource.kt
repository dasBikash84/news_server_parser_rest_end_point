package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.ErrorLogs
import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequest
import com.dasbikash.news_server_parser_rest_end_point.model.RequestDetailsBean
import com.dasbikash.news_server_parser_rest_end_point.services.ErrorLogService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("error-logs")
@Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
@Consumes(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
@Component
open class ErrorLogResource
constructor(open var errorLogService: ErrorLogService?=null,
            open var restControllerUtils: RestControllerUtils?=null) {

    @Value("\${log.default_page_size}")
    open var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    open var maxPageSize: Int = 50

    @GET
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getLatestErrorLogsEndPoint(@QueryParam("page-size") pageSizeRequest:Int?,
                                        @BeanParam requestDetails: RequestDetailsBean): Response {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(ErrorLogs(errorLogService!!.getLatestErrorLogs(pageSize)))
    }

    @GET
    @Path("/before/{log-id}")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getErrorLogsBeforeGivenIdEndPoint(@QueryParam("page-size") pageSizeRequest:Int?,
                                               @PathParam("log-id") lastErrorLogId:Int,
                                               @BeanParam requestDetails: RequestDetailsBean)
            : Response {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(ErrorLogs(
                errorLogService!!.getErrorLogsBeforeGivenId(lastErrorLogId,pageSize)))
    }

    @GET
    @Path("/after/{log-id}")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getErrorLogsAfterGivenIdEndPoint(@QueryParam("page-size") pageSizeRequest:Int?,
                                              @PathParam("log-id") lastErrorLogId:Int,
                                              @BeanParam requestDetails: RequestDetailsBean)
            : Response {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(ErrorLogs(
                errorLogService!!.getLogsAfterGivenId(lastErrorLogId,pageSize)))
    }

    @DELETE
    @Path("request_log_delete_token_generation")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun generateLogDeletionTokenEndPoint(@BeanParam requestDetails: RequestDetailsBean): Response {
        return restControllerUtils!!.generateLogDeleteToken(this::class.java)
    }

    @DELETE
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    @Consumes(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun deleteErrorLogsEndPoint(logEntryDeleteRequest: LogEntryDeleteRequest?,
                                     @BeanParam requestDetails: RequestDetailsBean): Response {
        return restControllerUtils!!.entityToResponseEntity(ErrorLogs(
                restControllerUtils!!.deleteLogEntries(errorLogService!!,logEntryDeleteRequest)))
    }
}
