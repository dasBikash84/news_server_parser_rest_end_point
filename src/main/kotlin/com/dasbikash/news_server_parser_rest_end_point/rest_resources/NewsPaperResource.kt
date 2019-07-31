package com.dasbikash.news_server_parser_rest_end_point.rest_resources

import com.dasbikash.news_server_parser_rest_end_point.model.NewsPaperParserModeChangeRequest
import com.dasbikash.news_server_parser_rest_end_point.model.NewsPaperStatusChangeRequest
import com.dasbikash.news_server_parser_rest_end_point.model.Newspapers
import com.dasbikash.news_server_parser_rest_end_point.model.RequestDetailsBean
import com.dasbikash.news_server_parser_rest_end_point.services.NewsPaperService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.stereotype.Component
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("newspapers")
@Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
@Consumes(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
@Component
open class NewsPaperResource
constructor(open var newsPaperService: NewsPaperService?=null,
            open var restControllerUtils: RestControllerUtils?=null) {

    @GET
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getAllActiveNewsPapersEndPoint(@BeanParam requestDetails: RequestDetailsBean): Response {
        return restControllerUtils!!.entityToResponseEntity(Newspapers(newsPaperService!!.getAllActiveNewsPapers()))
    }

    @GET
    @Path("all")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getAllNewsPapersEndPoint(@BeanParam requestDetails: RequestDetailsBean): Response {
        return restControllerUtils!!.entityToResponseEntity(Newspapers(newsPaperService!!.getAllNewsPapers()))
    }

    @GET
    @Path("request_newspaper_status_change_token_generation")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun generateNewspaperStatusChangeTokenEndPoint(@BeanParam requestDetails: RequestDetailsBean): Response {
        return restControllerUtils!!.generateNewspaperStatusChangeToken(this::class.java)
    }

    @GET
    @Path("request_newspaper_parser_mode_change_token_generation")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun generateNewspaperParserModeChangeTokenEndPoint(@BeanParam requestDetails: RequestDetailsBean): Response {
        return restControllerUtils!!.generateNewspaperParserModeChangeToken(this::class.java)
    }

    @POST
    @Path("request_newspaper_status_change")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    @Consumes(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun requestNewspaperStatusChangeEndPoint(newsPaperStatusChangeRequest: NewsPaperStatusChangeRequest?,
                                                  @BeanParam requestDetails: RequestDetailsBean)
            : Response {
        return restControllerUtils!!.entityToResponseEntity(
                newsPaperService!!.requestNewspaperStatusChange(newsPaperStatusChangeRequest))
    }

    @POST
    @Path("request_newspaper_parser_mode_change")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    @Consumes(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun requestNewspaperParserModeChangeEndPoint(newsPaperParserModeChangeRequest: NewsPaperParserModeChangeRequest?,
                                                      @BeanParam requestDetails: RequestDetailsBean)
            : Response {
        return restControllerUtils!!.entityToResponseEntity(
                newsPaperService!!.requestNewspaperParserModeChange(newsPaperParserModeChangeRequest))
    }

}
