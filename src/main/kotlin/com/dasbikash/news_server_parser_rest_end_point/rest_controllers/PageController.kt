package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.services.PageService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils

//@RestController
//@RequestMapping("pages",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class PageController (open var pageService: PageService,
                           open var restControllerUtils: RestControllerUtils){

    /*@GetMapping(value = arrayOf("","/"),produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getAllActivePagesEndPoint(@Autowired request: HttpServletRequest):ResponseEntity<Pages>{
        return restControllerUtils.entityToResponseEntity(
                Pages(pageService.getAllActivePages()))
    }

    @GetMapping("/newspaper_id/{newspaperId}",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getAllActivePagesByNewspaperIdEndPoint(@PathVariable("newspaperId") newspaperId:String,
                                            @Autowired request: HttpServletRequest):ResponseEntity<Pages>{
        return restControllerUtils.entityToResponseEntity(
                Pages(pageService.getAllActivePagesByNewspaperId(newspaperId)))
    }*/
}