package com.dasbikash.news_server_parser_rest_end_point

import com.dasbikash.news_server_parser_rest_end_point.exception_mappers.GenericExceptionMapper
import com.dasbikash.news_server_parser_rest_end_point.rest_controllers.*
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Configuration

@Configuration
open class JerseyConfig : ResourceConfig() {
    init {
        registerClasses(GenericExceptionMapper::class.java, LanguageResource::class.java,
                CountryResource::class.java, NewsPaperResource::class.java,
                PageResource::class.java, PageGroupResource::class.java, ArticleResource::class.java/*,
                ArticleDownloadLogResource::class.java,
                ArticleUploadLogResource::class.java, ArticleUploaderStatusChangeLogResource::class.java,
                ErrorLogResource::class.java, GeneralLogResource::class.java,
                SettingsUpdateLogResource::class.java, SettingsUploadLogResource::class.java*/)
    }
}
