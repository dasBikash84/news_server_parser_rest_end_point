package com.dasbikash.news_server_parser_rest_end_point.parser

import com.dasbikash.news_server_parser_rest_end_point.Init.SettingsBootstrapService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service

@Service
open class ParserService(
        private var settingsBootstrapService: SettingsBootstrapService?=null
)
    :CommandLineRunner {
    override fun run(vararg args: String?) {
        settingsBootstrapService!!.bootstrapSettingsIfRequired()
    }
}