package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.Countries
import com.dasbikash.news_server_parser_rest_end_point.services.CountryService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("countries")
open class CountryController
constructor(open var countryService: CountryService,
            open var restControllerUtils: RestControllerUtils) {

    @GetMapping(value = arrayOf("","/"))
    open fun getAllCountriesEndPoint(@Autowired request: HttpServletRequest):ResponseEntity<Countries>{
        return restControllerUtils.entityToResponseEntity(Countries(countryService.getAllCountries()))
    }

}
