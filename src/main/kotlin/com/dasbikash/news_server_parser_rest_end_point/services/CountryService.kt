package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.Country
import com.dasbikash.news_server_parser_rest_end_point.repositories.CountryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CountryService @Autowired
constructor(val countryRepository: CountryRepository){

    fun getAllCountries():List<Country>{
        return countryRepository.findAll()
    }
}