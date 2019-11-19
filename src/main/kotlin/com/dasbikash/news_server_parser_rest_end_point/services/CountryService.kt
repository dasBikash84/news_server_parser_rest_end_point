package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.Country
import com.dasbikash.news_server_parser_rest_end_point.repositories.CountryRepository
import org.springframework.stereotype.Service

@Service
open class CountryService
constructor(private var countryRepository: CountryRepository?=null){

    fun getAllCountries():List<Country>{
        return countryRepository!!.findAll()
    }

    fun getCount(): Long {
        return countryRepository!!.count()
    }

    fun save(country: Country):Country {
        return countryRepository!!.save(country)
    }
}