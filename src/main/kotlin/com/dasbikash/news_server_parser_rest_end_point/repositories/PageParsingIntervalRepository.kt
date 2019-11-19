package com.dasbikash.news_server_parser_rest_end_point.repositories

import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.model.database.PageParsingInterval
import org.springframework.data.jpa.repository.JpaRepository

interface PageParsingIntervalRepository : JpaRepository<PageParsingInterval, Int>{
    fun getPageParsingIntervalsByPage(page: Page):List<PageParsingInterval>
}