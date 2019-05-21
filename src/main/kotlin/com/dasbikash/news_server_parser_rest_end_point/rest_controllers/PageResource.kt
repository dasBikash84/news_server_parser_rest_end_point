package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.Pages
import com.dasbikash.news_server_parser_rest_end_point.model.RequestDetailsBean
import com.dasbikash.news_server_parser_rest_end_point.services.PageService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.stereotype.Component
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("pages")
@Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
@Consumes(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
@Component
open class PageResource (open var pageService: PageService?=null,
                         open var restControllerUtils: RestControllerUtils?=null){

    @GET
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getAllActivePagesEndPoint(@BeanParam requestDetails: RequestDetailsBean) =
            restControllerUtils!!.entityToResponseEntity(Pages(pageService!!.getAllActivePages()))

    @GET
    @Path("/newspaper_id/{newspaperId}")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getAllActivePagesByNewspaperIdEndPoint(@PathParam("newspaperId") newspaperId:String,
                                                    @BeanParam requestDetails: RequestDetailsBean):Response{
        return restControllerUtils!!.entityToResponseEntity(
                Pages(pageService!!.getAllActivePagesByNewspaperId(newspaperId)))
    }
}