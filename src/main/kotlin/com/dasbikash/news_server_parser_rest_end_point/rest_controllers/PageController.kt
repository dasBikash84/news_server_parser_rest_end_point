package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.model.database.Pages
import com.dasbikash.news_server_parser_rest_end_point.services.PageService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("pages")
class PageController (@Autowired val pageService: PageService){

    @GetMapping(value = arrayOf("","/"))
    fun getAllActivePages():ResponseEntity<Pages>{
        return RestControllerUtills.entityToResponseEntity(
                Pages(pageService.getAllActivePages())
        )
    }
}