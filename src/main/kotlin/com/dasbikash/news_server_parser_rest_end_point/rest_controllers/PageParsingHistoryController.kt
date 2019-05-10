package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequest
import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_parser_rest_end_point.model.PageParsingHistories
import com.dasbikash.news_server_parser_rest_end_point.services.PageParsingHistoryService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("page-parsing-histories")
class PageParsingHistoryController @Autowired
constructor(val pageParsingHistoryService: PageParsingHistoryService,
            val restControllerUtils: RestControllerUtils) {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50

    @GetMapping("")
    fun getLatestPageParsingHistories(@RequestParam("page-size") pageSizeRequest:Int?): ResponseEntity<PageParsingHistories> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(PageParsingHistories(pageParsingHistoryService.getLatestPageParsingHistories(pageSize)))
    }

    @GetMapping("/before/error-log-id/{log-id}")
    fun getPageParsingHistoriesBeforeGivenId(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @PathVariable("log-id") lastErrorLogId:Int)
            : ResponseEntity<PageParsingHistories> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(PageParsingHistories(
                        pageParsingHistoryService.getPageParsingHistoriesBeforeGivenId(lastErrorLogId,pageSize)))
    }

    @DeleteMapping("request_log_delete_token_generation")
    fun generateLogDeletionToken(): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtils.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("")
    fun deleteErrorLogs(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?): ResponseEntity<PageParsingHistories> {
        return restControllerUtils.entityToResponseEntity(PageParsingHistories(
                restControllerUtils.deleteLogEntries(pageParsingHistoryService,logEntryDeleteRequest)))
    }
}
