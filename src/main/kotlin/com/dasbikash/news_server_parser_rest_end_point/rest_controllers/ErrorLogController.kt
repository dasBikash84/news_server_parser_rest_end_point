package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.ErrorLogs
import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequest
import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_parser_rest_end_point.services.ErrorLogService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("error-logs")
open class ErrorLogController
constructor(open var errorLogService: ErrorLogService,
            open var restControllerUtils: RestControllerUtils) {

    @Value("\${log.default_page_size}")
    open var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    open var maxPageSize: Int = 50

    @GetMapping("")
    open fun getLatestErrorLogsEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @Autowired request: HttpServletRequest): ResponseEntity<ErrorLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(ErrorLogs(errorLogService.getLatestErrorLogs(pageSize)))
    }

    @GetMapping("/before/{log-id}")
    open fun getErrorLogsBeforeGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                @PathVariable("log-id") lastErrorLogId:Int,
                                               @Autowired request: HttpServletRequest)
            : ResponseEntity<ErrorLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(ErrorLogs(
                        errorLogService.getErrorLogsBeforeGivenId(lastErrorLogId,pageSize)))
    }

    @GetMapping("/after/{log-id}")
    open fun getErrorLogsAfterGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                @PathVariable("log-id") lastErrorLogId:Int,
                                              @Autowired request: HttpServletRequest)
            : ResponseEntity<ErrorLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(ErrorLogs(
                        errorLogService.getLogsAfterGivenId(lastErrorLogId,pageSize)))
    }

    @DeleteMapping("request_log_delete_token_generation")
    open fun generateLogDeletionTokenEndPoint(@Autowired request: HttpServletRequest): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtils.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("")
    open fun deleteErrorLogsEndPoint(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?,
                                     @Autowired request: HttpServletRequest): ResponseEntity<ErrorLogs> {
        return restControllerUtils.entityToResponseEntity(ErrorLogs(
                restControllerUtils.deleteLogEntries(errorLogService,logEntryDeleteRequest)))
    }
}
