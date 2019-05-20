package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.services.NewsPaperService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils

//@RestController
//@RequestMapping("newspapers",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class NewsPaperController
constructor(open var newsPaperService: NewsPaperService,
            open var restControllerUtils: RestControllerUtils) {

    /*@GetMapping(value = arrayOf("","/"),produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
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
    }*/

}
