package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.PageGroup
import com.dasbikash.news_server_parser_rest_end_point.repositories.PageGroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PageGroupService @Autowired
constructor(val pageGroupRepository: PageGroupRepository){

    fun getAllPageGroups():List<PageGroup>{
        return pageGroupRepository.findAll()
    }
}