package com.dasbikash.news_server_parser_rest_end_point.repositories

import com.dasbikash.news_server_parser_rest_end_point.model.database.Newspaper
import com.dasbikash.news_server_parser_rest_end_point.model.database.NewspaperOpModeEntry
import org.springframework.data.jpa.repository.JpaRepository

interface NewspaperOpModeEntryRepository : JpaRepository<NewspaperOpModeEntry, Int>{
    fun findAllByNewspaper(newspaper: Newspaper):List<NewspaperOpModeEntry>
    fun getAllByNewspaperOrderByIdDesc(newspaper: Newspaper):List<NewspaperOpModeEntry>
}