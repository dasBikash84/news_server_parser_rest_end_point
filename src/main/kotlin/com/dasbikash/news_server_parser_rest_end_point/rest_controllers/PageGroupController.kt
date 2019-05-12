package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.PageGroups
import com.dasbikash.news_server_parser_rest_end_point.services.PageGroupService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("page-groups")
class PageGroupController @Autowired
constructor(private val pageGroupService: PageGroupService,
            private val restControllerUtils: RestControllerUtils) {

    @GetMapping(value = arrayOf("","/"))
    fun getAllCountries():ResponseEntity<PageGroups>{
        return restControllerUtils.entityToResponseEntity(PageGroups(pageGroupService.getAllPageGroups()))
    }

}