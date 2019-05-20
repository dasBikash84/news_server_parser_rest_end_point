package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequest
import com.dasbikash.news_server_parser_rest_end_point.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_parser_rest_end_point.model.PageParsingHistories
import com.dasbikash.news_server_parser_rest_end_point.services.PageParsingHistoryService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("page-parsing-histories",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class PageParsingHistoryController
constructor(open var pageParsingHistoryService: PageParsingHistoryService,
            open var restControllerUtils: RestControllerUtils) {

    @Value("\${log.default_page_size}")
    open var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    open var maxPageSize: Int = 50

    @GetMapping("",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getLatestPageParsingHistoriesEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                   @Autowired request: HttpServletRequest)
            : ResponseEntity<PageParsingHistories> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(PageParsingHistories(pageParsingHistoryService.getLatestPageParsingHistories(pageSize)))
    }

    @GetMapping("/before/{log-id}",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getPageParsingHistoriesBeforeGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                            @PathVariable("log-id") lastErrorLogId:Int,
                                                          @Autowired request: HttpServletRequest)
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

    @GetMapping("/after/{log-id}",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getPageParsingHistoriesAfterGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                            @PathVariable("log-id") lastErrorLogId:Int,
                                                         @Autowired request: HttpServletRequest)
            : ResponseEntity<PageParsingHistories> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(PageParsingHistories(
                        pageParsingHistoryService.getLogsAfterGivenId(lastErrorLogId,pageSize)))
    }

    @DeleteMapping("request_log_delete_token_generation",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun generateLogDeletionTokenEndPoint(@Autowired request: HttpServletRequest)
            : ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtils.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun deleteErrorLogsEndPoint(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?,
                                     @Autowired request: HttpServletRequest)
            : ResponseEntity<PageParsingHistories> {
        return restControllerUtils.entityToResponseEntity(PageParsingHistories(
                restControllerUtils.deleteLogEntries(pageParsingHistoryService,logEntryDeleteRequest)))
    }
}
