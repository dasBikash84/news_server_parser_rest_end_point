package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.services.LanguageService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils

//@RestController
//@RequestMapping("languages",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class LanguageController
constructor(open var languageService: LanguageService,
            open var restControllerUtils: RestControllerUtils) {

    /*@GetMapping(value = arrayOf("","/"),produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getAllLanguagesEndPoint(@Autowired request: HttpServletRequest):ResponseEntity<Languages>{
        return restControllerUtils.entityToResponseEntity(Languages(languageService.getAllLanguages()))
    }*/

}
