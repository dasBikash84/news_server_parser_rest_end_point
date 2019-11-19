package com.dasbikash.news_server_parser_rest_end_point.rest_resources

import com.dasbikash.news_server_parser_rest_end_point.model.RequestDetailsBean
import com.dasbikash.news_server_parser_rest_end_point.services.SettingsGenerationService
import org.springframework.stereotype.Component
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("current-settings")
@Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
@Consumes(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
@Component
open class SettingsGenerationResource(
        open var settingsGenerationService: SettingsGenerationService?=null
) {

    @GET
    @Path("/generate")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    fun getSettingFilesEndPoint(@BeanParam requestDetails: RequestDetailsBean): Response {
        settingsGenerationService!!.generateSettingsData()
        return Response.ok().build()
    }
}