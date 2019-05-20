package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.Country
import com.dasbikash.news_server_parser_rest_end_point.repositories.CountryRepository
import org.springframework.stereotype.Service

@Service
open class CountryService
constructor(open var countryRepository: CountryRepository){

    fun getAllCountries():List<Country>{
        return countryRepository.findAll()
    }
}