package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.Language
import com.dasbikash.news_server_parser_rest_end_point.repositories.LanguageRepository
import org.springframework.stereotype.Service

@Service
open class LanguageService(open var languageRepository: LanguageRepository) {

    fun getAllLanguages(): List<Language> {
        return languageRepository.findAll()
    }
}
