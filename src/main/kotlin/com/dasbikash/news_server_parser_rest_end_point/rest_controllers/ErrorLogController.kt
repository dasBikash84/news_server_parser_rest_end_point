package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.services.ErrorLogService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Value

//@RestController
//@RequestMapping("error-logs",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class ErrorLogController
constructor(open var errorLogService: ErrorLogService,
            open var restControllerUtils: RestControllerUtils) {

    @Value("\${log.default_page_size}")
    open var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    open var maxPageSize: Int = 50

    /*@GetMapping("",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
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

    @GetMapping("/before/{log-id}",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
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

    @GetMapping("/after/{log-id}",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
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

    @DeleteMapping("request_log_delete_token_generation",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun generateLogDeletionTokenEndPoint(@Autowired request: HttpServletRequest): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtils.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun deleteErrorLogsEndPoint(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?,
                                     @Autowired request: HttpServletRequest): ResponseEntity<ErrorLogs> {
        return restControllerUtils.entityToResponseEntity(ErrorLogs(
                restControllerUtils.deleteLogEntries(errorLogService,logEntryDeleteRequest)))
    }*/
}
