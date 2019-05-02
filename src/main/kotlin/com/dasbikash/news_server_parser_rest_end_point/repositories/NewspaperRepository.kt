package com.dasbikash.news_server_parser_rest_end_point.repositories

import com.dasbikash.news_server_parser_rest_end_point.model.database.Newspaper
import org.springframework.data.jpa.repository.JpaRepository

interface NewspaperRepository : JpaRepository<Newspaper, String>{
    fun findAllByActive(active:Boolean=true):List<Newspaper>
}