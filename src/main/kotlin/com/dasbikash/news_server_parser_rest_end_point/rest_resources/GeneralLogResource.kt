package com.dasbikash.news_server_parser_rest_end_point.rest_resources

import com.dasbikash.news_server_parser_rest_end_point.model.GeneralLogs
import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequest
import com.dasbikash.news_server_parser_rest_end_point.model.RequestDetailsBean
import com.dasbikash.news_server_parser_rest_end_point.services.GeneralLogService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("general-logs")
@Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
@Consumes(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
@Component
open class GeneralLogResource
constructor(open var generalLogService: GeneralLogService?=null,
            open var restControllerUtils: RestControllerUtils?=null) {

    @Value("\${log.default_page_size}")
    open var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    open var maxPageSize: Int = 50

    @GET
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getLatestGeneralLogsEndPoint(@QueryParam("page-size") pageSizeRequest: Int?,
                                          @BeanParam requestDetails: RequestDetailsBean): Response {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(GeneralLogs(generalLogService!!.getLatestGeneralLogs(pageSize)))
    }

    @GET
    @Path("/before/{log-id}")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getGeneralLogsBeforeGivenIdEndPoint(@QueryParam("page-size") pageSizeRequest: Int?,
                                                 @PathParam("log-id") lastGeneralLogId: Int,
                                                 @BeanParam requestDetails: RequestDetailsBean)
            : Response {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(GeneralLogs(
                generalLogService!!.getGeneralLogsBeforeGivenId(lastGeneralLogId, pageSize)))
    }

    @GET
    @Path("/after/{log-id}")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getGeneralLogsAfterGivenIdEndPoint(@QueryParam("page-size") pageSizeRequest: Int?,
                                                @PathParam("log-id") lastGeneralLogId: Int,
                                                @BeanParam requestDetails: RequestDetailsBean)
            : Response {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(GeneralLogs(
                generalLogService!!.getLogsAfterGivenId(lastGeneralLogId, pageSize)))
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
    open fun deleteGeneralLogsEndPoint(logEntryDeleteRequest: LogEntryDeleteRequest?,
                                       @BeanParam requestDetails: RequestDetailsBean): Response {
        return restControllerUtils!!.entityToResponseEntity(GeneralLogs(
                restControllerUtils!!.deleteLogEntries(generalLogService!!,logEntryDeleteRequest)))
    }
}
