package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.NewsCategoryEntry
import com.dasbikash.news_server_parser_rest_end_point.repositories.NewsCategoryEntryRepository
import org.springframework.stereotype.Service

@Service
open class NewsCategoryEntryService
constructor(open var newsCategoryEntryService: NewsCategoryEntryRepository){

    fun getAllNewsCategoryEntries():List<NewsCategoryEntry>{
        return newsCategoryEntryService.findAll()
    }
}