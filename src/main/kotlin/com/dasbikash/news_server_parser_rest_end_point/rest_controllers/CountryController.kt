package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.database.Countries
import com.dasbikash.news_server_parser_rest_end_point.services.CountryService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("countries")
class CountryController @Autowired
constructor(private val countryService: CountryService) {

    @GetMapping(value = arrayOf("","/"))
    fun getAllCountries():ResponseEntity<Countries>{
        return RestControllerUtills.entityToResponseEntity(Countries(countryService.getAllCountries()))
    }

}
