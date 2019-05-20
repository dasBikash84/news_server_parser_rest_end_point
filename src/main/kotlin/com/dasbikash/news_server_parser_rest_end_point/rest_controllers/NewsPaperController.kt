package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.NewsPaperStatusChangeRequest
import com.dasbikash.news_server_parser_rest_end_point.model.NewsPaperStatusChangeRequestFormat
import com.dasbikash.news_server_parser_rest_end_point.model.Newspapers
import com.dasbikash.news_server_parser_rest_end_point.model.database.Newspaper
import com.dasbikash.news_server_parser_rest_end_point.services.NewsPaperService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("newspapers",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class NewsPaperController
constructor(open var newsPaperService: NewsPaperService,
            open var restControllerUtils: RestControllerUtils) {

    @GetMapping(value = arrayOf("","/"),produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getAllActiveNewsPapersEndPoint(@Autowired request: HttpServletRequest):ResponseEntity<Newspapers>{
        return restControllerUtils.entityToResponseEntity(Newspapers(newsPaperService.getAllActiveNewsPapers()))
    }

    @GetMapping("request_newspaper_status_change_token_generation",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun generateNewspaperStatusChangeTokenEndPoint(@Autowired request: HttpServletRequest): ResponseEntity<NewsPaperStatusChangeRequestFormat> {
        return restControllerUtils.generateNewspaperStatusChangeToken(this::class.java)
    }

    @PostMapping("request_newspaper_status_change",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun requestNewspaperStatusChangeEndPoint(@RequestBody newsPaperStatusChangeRequest: NewsPaperStatusChangeRequest?,
                                                  @Autowired request: HttpServletRequest)
            : ResponseEntity<Newspaper> {
        return restControllerUtils.entityToResponseEntity(
                newsPaperService.requestNewspaperStatusChange(newsPaperStatusChangeRequest))
    }

}
