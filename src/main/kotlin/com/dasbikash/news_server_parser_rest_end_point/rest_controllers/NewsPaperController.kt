package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.NewsPaperStatusChangeRequest
import com.dasbikash.news_server_parser_rest_end_point.model.NewsPaperStatusChangeRequestFormat
import com.dasbikash.news_server_parser_rest_end_point.model.Newspapers
import com.dasbikash.news_server_parser_rest_end_point.services.NewsPaperService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("newspapers")
class NewsPaperController @Autowired
constructor(private val newsPaperService: NewsPaperService,
            private val restControllerUtils: RestControllerUtils) {

    @GetMapping(value = arrayOf("","/"))
    fun getAllActiveNewsPapers():ResponseEntity<Newspapers>{
        return restControllerUtils.entityToResponseEntity(Newspapers(newsPaperService.getAllActiveNewsPapers()))
    }

    @GetMapping("request_newspaper_status_change_token_generation")
    fun generateNewspaperStatusChangeToken(): ResponseEntity<NewsPaperStatusChangeRequestFormat> {
        return restControllerUtils.generateNewspaperStatusChangeToken(this::class.java)
    }

    @PostMapping("request_newspaper_status_change")
    fun requestNewspaperStatusChange(@RequestBody newsPaperStatusChangeRequest: NewsPaperStatusChangeRequest?)
            : ResponseEntity<NewsPaperStatusChangeRequest> {
        return restControllerUtils.entityToResponseEntity(
                newsPaperService.requestNewspaperStatusChange(newsPaperStatusChangeRequest))
    }

}
