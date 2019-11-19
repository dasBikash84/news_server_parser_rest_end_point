package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.NewspaperOpModeEntry
import com.dasbikash.news_server_parser_rest_end_point.repositories.NewspaperOpModeEntryRepository
import org.springframework.stereotype.Service

@Service
open class NewspaperOpModeEntryService (
        private var newspaperOpModeEntryRepository: NewspaperOpModeEntryRepository?=null
){

    fun getCount(): Long {
        return newspaperOpModeEntryRepository!!.count()
    }

    fun save(newspaperOpModeEntry: NewspaperOpModeEntry):NewspaperOpModeEntry {
        return newspaperOpModeEntryRepository!!.save(newspaperOpModeEntry)
    }

    fun saveAll(newspaperOpModeEntries: Collection<NewspaperOpModeEntry>):List<NewspaperOpModeEntry> {
        return newspaperOpModeEntryRepository!!.saveAll(newspaperOpModeEntries)
    }
}