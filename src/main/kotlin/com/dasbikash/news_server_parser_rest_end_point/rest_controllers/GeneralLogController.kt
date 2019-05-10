package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.GeneralLogs
import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequest
import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_parser_rest_end_point.services.GeneralLogService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("general-logs")
class GeneralLogController @Autowired
constructor(val generalLogService: GeneralLogService,
            val restControllerUtils: RestControllerUtils) {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50

    @GetMapping("")
    fun getLatestGeneralLogs(@RequestParam("page-size") pageSizeRequest: Int?): ResponseEntity<GeneralLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(GeneralLogs(generalLogService.getLatestGeneralLogs(pageSize)))
    }

    @GetMapping("/before/general-log-id/{log-id}")
    fun getGeneralLogsBeforeGivenId(@RequestParam("page-size") pageSizeRequest: Int?,
                                    @PathVariable("log-id") lastGeneralLogId: Int)
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

    @DeleteMapping("request_log_delete_token_generation")
    fun generateLogDeletionToken(): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtils.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("")
    fun deleteGeneralLogs(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?): ResponseEntity<GeneralLogs> {
        return restControllerUtils.entityToResponseEntity(GeneralLogs(
                restControllerUtils.deleteLogEntries(generalLogService,logEntryDeleteRequest)))
    }
}
