package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.database.Language
import com.dasbikash.news_server_parser_rest_end_point.model.database.Languages
import com.dasbikash.news_server_parser_rest_end_point.services.LanguageService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("languages")
class LanguageController @Autowired
constructor(private val languageService: LanguageService) {

    @GetMapping(value = arrayOf("","/"))
    fun getAllLanguages():ResponseEntity<Languages>{
        return RestControllerUtills.entityToResponseEntity(Languages(languageService.getAllLanguages()))
    }

}
