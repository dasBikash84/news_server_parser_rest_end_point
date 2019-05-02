package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.Language
import com.dasbikash.news_server_parser_rest_end_point.repositories.LanguageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LanguageService(@Autowired val languageRepository: LanguageRepository) {

    fun getAllLanguages(): List<Language> {
        return languageRepository.findAll()
    }
}
