package com.dasbikash.news_server_parser_rest_end_point.repositories

import com.dasbikash.news_server_parser_rest_end_point.model.database.NewsCategory
import org.springframework.data.jpa.repository.JpaRepository

interface NewsCategoryRepository : JpaRepository<NewsCategory, String>{
}