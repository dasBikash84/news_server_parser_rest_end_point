package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.GeneralLogs
import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequest
import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_parser_rest_end_point.services.GeneralLogService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("general-logs",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class GeneralLogController
constructor(open var generalLogService: GeneralLogService,
            open var restControllerUtils: RestControllerUtils) {

    @Value("\${log.default_page_size}")
    open var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    open var maxPageSize: Int = 50

    @GetMapping("",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getLatestGeneralLogsEndPoint(@RequestParam("page-size") pageSizeRequest: Int?,
                                  @Autowired request: HttpServletRequest): ResponseEntity<GeneralLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(GeneralLogs(generalLogService.getLatestGeneralLogs(pageSize)))
    }

    @GetMapping("/before/{log-id}",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getGeneralLogsBeforeGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest: Int?,
                                            @PathVariable("log-id") lastGeneralLogId: Int,
                                         @Autowired request: HttpServletRequest)
            : ResponseEntity<GeneralLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(GeneralLogs(
                generalLogService.getGeneralLogsBeforeGivenId(lastGeneralLogId, pageSize)))
    }

    @GetMapping("/after/{log-id}",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getGeneralLogsAfterGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest: Int?,
                                        @PathVariable("log-id") lastGeneralLogId: Int,
                                        @Autowired request: HttpServletRequest)
            : ResponseEntity<GeneralLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(GeneralLogs(
                generalLogService.getLogsAfterGivenId(lastGeneralLogId, pageSize)))
    }

    @DeleteMapping("request_log_delete_token_generation",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun generateLogDeletionTokenEndPoint(@Autowired request: HttpServletRequest): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtils.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun deleteGeneralLogsEndPoint(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?,
                               @Autowired request: HttpServletRequest): ResponseEntity<GeneralLogs> {
        return restControllerUtils.entityToResponseEntity(GeneralLogs(
                restControllerUtils.deleteLogEntries(generalLogService,logEntryDeleteRequest)))
    }
}
