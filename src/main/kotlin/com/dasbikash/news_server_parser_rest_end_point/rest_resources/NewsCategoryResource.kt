package com.dasbikash.news_server_parser_rest_end_point.rest_resources

import com.dasbikash.news_server_parser_rest_end_point.model.NewsCategories
import com.dasbikash.news_server_parser_rest_end_point.model.RequestDetailsBean
import com.dasbikash.news_server_parser_rest_end_point.services.NewsCategoryService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.stereotype.Component
import javax.ws.rs.BeanParam
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("news_categories")
@Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
@Component
open class NewsCategoryResource
constructor(open var newsCategoryService: NewsCategoryService?=null,
            open var restControllerUtils: RestControllerUtils?=null) {

    @GET
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getAllNewsCategoriesEndPoint(@BeanParam requestDetails: RequestDetailsBean): Response {
        return restControllerUtils!!.entityToResponseEntity(NewsCategories(newsCategoryService!!.getAllNewsCategories()))
    }

}
