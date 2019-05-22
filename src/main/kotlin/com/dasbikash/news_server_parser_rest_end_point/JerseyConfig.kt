package com.dasbikash.news_server_parser_rest_end_point

import com.dasbikash.news_server_parser_rest_end_point.container_filters.LoggingFilter
import com.dasbikash.news_server_parser_rest_end_point.exception_mappers.GenericExceptionMapper
import com.dasbikash.news_server_parser_rest_end_point.rest_resources.*
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Configuration

@Configuration
open class JerseyConfig : ResourceConfig() {
    init {
        registerClasses(GenericExceptionMapper::class.java, LanguageResource::class.java,
                        CountryResource::class.java, NewsPaperResource::class.java,
                        PageResource::class.java, PageGroupResource::class.java, ArticleResource::class.java,
                        GeneralLogResource::class.java,ErrorLogResource::class.java,PageParsingHistoryResource::class.java,
                        LoggingFilter::class.java)
    }
}
