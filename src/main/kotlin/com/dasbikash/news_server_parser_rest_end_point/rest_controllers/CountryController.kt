package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.services.CountryService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils

//@RestController
//@RequestMapping("countries",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class CountryController
constructor(open var countryService: CountryService,
            open var restControllerUtils: RestControllerUtils) {

    /*@GetMapping(value = arrayOf("","/"),produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getAllCountriesEndPoint(@Autowired request: HttpServletRequest):ResponseEntity<Countries>{
        return restControllerUtils.entityToResponseEntity(Countries(countryService.getAllCountries()))
    }*/

}
