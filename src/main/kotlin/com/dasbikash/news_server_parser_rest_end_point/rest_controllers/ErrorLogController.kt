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

@RestController
@RequestMapping("error-logs")
class ErrorLogController @Autowired
constructor(val errorLogService: ErrorLogService,
            val restControllerUtils: RestControllerUtils) {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50

    @GetMapping("")
    fun getLatestErrorLogs(@RequestParam("page-size") pageSizeRequest:Int?): ResponseEntity<ErrorLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(ErrorLogs(errorLogService.getLatestErrorLogs(pageSize)))
    }

    @GetMapping("/before/error-log-id/{log-id}")
    fun getErrorLogsBeforeGivenId(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @PathVariable("log-id") lastErrorLogId:Int)
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

    @DeleteMapping("request_log_delete_token_generation")
    fun generateLogDeletionToken(): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtils.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("")
    fun deleteErrorLogs(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?): ResponseEntity<ErrorLogs> {
        return restControllerUtils.entityToResponseEntity(ErrorLogs(
                restControllerUtils.deleteLogEntries(errorLogService,logEntryDeleteRequest)))
    }
}
