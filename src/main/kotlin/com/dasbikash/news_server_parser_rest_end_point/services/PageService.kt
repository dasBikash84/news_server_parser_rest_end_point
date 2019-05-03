package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.DataNotFoundException
import com.dasbikash.news_server_parser_rest_end_point.model.database.Newspaper
import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.repositories.NewspaperRepository
import com.dasbikash.news_server_parser_rest_end_point.repositories.PageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PageService @Autowired
constructor(val pageRepository: PageRepository,val newspaperRepository: NewspaperRepository) {

    fun getAllActivePages(): List<Page> {
        return pageRepository.findAllByActive()
    }

    fun getAllActivePagesByNewspaperId(newspaperId: String): List<Page> {
        val newspaperOptional = newspaperRepository.findById(newspaperId)
        if (!newspaperOptional.isPresent){
            throw DataNotFoundException()
        }
        return pageRepository.findPagesByNewspaperAndActive(newspaperOptional.get())
    }
}