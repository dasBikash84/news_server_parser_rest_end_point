package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.PageGroups
import com.dasbikash.news_server_parser_rest_end_point.services.PageGroupService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("page-groups",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class PageGroupController
constructor(open var pageGroupService: PageGroupService,
            open var restControllerUtils: RestControllerUtils) {

    @GetMapping(value = arrayOf("","/"),produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getAllCountriesEndPoint(@Autowired request: HttpServletRequest):ResponseEntity<PageGroups>{
        return restControllerUtils.entityToResponseEntity(PageGroups(pageGroupService.getAllPageGroups()))
    }

}
