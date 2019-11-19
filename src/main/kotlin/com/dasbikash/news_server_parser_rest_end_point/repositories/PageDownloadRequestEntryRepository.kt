package com.dasbikash.news_server_parser_rest_end_point.repositories

import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.model.database.PageDownloadRequestEntry
import org.springframework.data.jpa.repository.JpaRepository

interface PageDownloadRequestEntryRepository : JpaRepository<PageDownloadRequestEntry, Int>{
    fun getAllByPageAndActive(page: Page,active:Boolean):List<PageDownloadRequestEntry>
}