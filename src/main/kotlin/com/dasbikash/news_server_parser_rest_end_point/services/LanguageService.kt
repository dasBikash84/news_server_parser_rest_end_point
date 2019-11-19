package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.Language
import com.dasbikash.news_server_parser_rest_end_point.repositories.LanguageRepository
import org.springframework.stereotype.Service

@Service
open class LanguageService(private var languageRepository: LanguageRepository?=null) {

    fun getAllLanguages(): List<Language> {
        return languageRepository!!.findAll()
    }

    fun getCount(): Long {
        return languageRepository!!.count()
    }

    fun save(language: Language):Language {
        return languageRepository!!.save(language)
    }
}
