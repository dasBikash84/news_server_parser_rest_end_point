package com.dasbikash.news_server_parser_rest_end_point.repositories

import com.dasbikash.news_server_parser_rest_end_point.model.database.NewsCategoryEntry
import org.springframework.data.jpa.repository.JpaRepository

interface NewsCategoryEntryRepository : JpaRepository<NewsCategoryEntry, Int>{
}