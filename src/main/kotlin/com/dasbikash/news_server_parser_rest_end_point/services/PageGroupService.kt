package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.DataNotFoundException
import com.dasbikash.news_server_parser_rest_end_point.model.database.PageGroup
import com.dasbikash.news_server_parser_rest_end_point.repositories.PageGroupRepository
import org.springframework.stereotype.Service

@Service
open class PageGroupService
constructor(open var pageGroupRepository: PageGroupRepository){

    fun getAllPageGroups():List<PageGroup>{
        val pageGroups = pageGroupRepository.findAll()
        if (pageGroups.size == 0){
            throw DataNotFoundException()
        }
        return pageGroups
    }
}