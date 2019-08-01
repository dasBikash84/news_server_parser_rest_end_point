package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.NewsCategory
import com.dasbikash.news_server_parser_rest_end_point.repositories.NewsCategoryRepository
import org.springframework.stereotype.Service

@Service
open class NewsCategoryService
constructor(open var newsCategoryRepository: NewsCategoryRepository){

    fun getAllNewsCategories():List<NewsCategory>{
        return newsCategoryRepository.findAll()
    }
}