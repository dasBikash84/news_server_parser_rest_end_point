package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.model.database.PageParsingInterval
import com.dasbikash.news_server_parser_rest_end_point.repositories.PageParsingIntervalRepository
import org.springframework.stereotype.Service

@Service
open class PageParsingIntervalService(
        private var pageParsingIntervalRepository: PageParsingIntervalRepository?=null
){
    fun getPageParsingIntervalForPage(page: Page): PageParsingInterval? {
        pageParsingIntervalRepository!!
                .getPageParsingIntervalsByPage(page).apply {
                    if (isNotEmpty()){
                        return get(0)
                    }
                }
        return null
    }

    fun save(pageParsingInterval: PageParsingInterval):PageParsingInterval {
        return pageParsingIntervalRepository!!.save(pageParsingInterval)
    }
}