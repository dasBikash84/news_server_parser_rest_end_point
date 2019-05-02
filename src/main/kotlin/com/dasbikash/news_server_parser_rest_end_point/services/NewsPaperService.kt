package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.Newspaper
import com.dasbikash.news_server_parser_rest_end_point.repositories.NewspaperRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NewsPaperService @Autowired
constructor(val newspaperRepository: NewspaperRepository){

    fun getAllActiveNewsPapers():List<Newspaper>{
        return newspaperRepository.findAllByActive()
    }
}