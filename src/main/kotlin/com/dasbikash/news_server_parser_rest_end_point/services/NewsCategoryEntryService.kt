package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.NewsCategoryEntry
import com.dasbikash.news_server_parser_rest_end_point.repositories.NewsCategoryEntryRepository
import org.springframework.stereotype.Service

@Service
open class NewsCategoryEntryService
constructor(private var newsCategoryEntryService: NewsCategoryEntryRepository?=null){

    fun getAllNewsCategoryEntries():List<NewsCategoryEntry>{
        return newsCategoryEntryService!!.findAll()
    }

    fun getCount(): Long {
        return newsCategoryEntryService!!.count()
    }

    fun save(newsCategoryEntry: NewsCategoryEntry):NewsCategoryEntry {
        return newsCategoryEntryService!!.save(newsCategoryEntry)
    }

    fun saveAll(newsCategoryEntries: Collection<NewsCategoryEntry>):List<NewsCategoryEntry> {
        return newsCategoryEntryService!!.saveAll(newsCategoryEntries)
    }
}