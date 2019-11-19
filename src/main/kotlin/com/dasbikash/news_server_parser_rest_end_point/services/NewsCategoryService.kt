package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.NewsCategory
import com.dasbikash.news_server_parser_rest_end_point.repositories.NewsCategoryRepository
import org.springframework.stereotype.Service

@Service
open class NewsCategoryService
constructor(private var newsCategoryRepository: NewsCategoryRepository?=null){

    fun getAllNewsCategories():List<NewsCategory>{
        return newsCategoryRepository!!.findAll()
    }

    fun getCount(): Long {
        return newsCategoryRepository!!.count()
    }

    fun save(newsCategory: NewsCategory):NewsCategory {
        return newsCategoryRepository!!.save(newsCategory)
    }

    fun saveAll(newsCategories: Collection<NewsCategory>):List<NewsCategory> {
        return newsCategoryRepository!!.saveAll(newsCategories)
    }
}