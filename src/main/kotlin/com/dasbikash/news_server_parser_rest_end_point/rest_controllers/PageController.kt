package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.Pages
import com.dasbikash.news_server_parser_rest_end_point.services.PageService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("pages")
open class PageController (open var pageService: PageService,
                           open var restControllerUtils: RestControllerUtils){

    @GetMapping(value = arrayOf("","/"))
    open fun getAllActivePagesEndPoint(@Autowired request: HttpServletRequest):ResponseEntity<Pages>{
        return restControllerUtils.entityToResponseEntity(
                Pages(pageService.getAllActivePages()))
    }

    @GetMapping("/newspaper_id/{newspaperId}")
    open fun getAllActivePagesByNewspaperIdEndPoint(@PathVariable("newspaperId") newspaperId:String,
                                            @Autowired request: HttpServletRequest):ResponseEntity<Pages>{
        return restControllerUtils.entityToResponseEntity(
                Pages(pageService.getAllActivePagesByNewspaperId(newspaperId)))
    }
}