package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.*
import com.dasbikash.news_server_parser_rest_end_point.utills.FileUtils
import com.dasbikash.news_server_parser_rest_end_point.utills.LoggerService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.stereotype.Service


@Service
open class SettingsGenerationService(
    private var languageService: LanguageService?=null,
    private var countryService: CountryService?=null,
    private var newsPaperService: NewsPaperService?=null,
    private var loggerService: LoggerService?=null,
    private var newsCategoryService: NewsCategoryService?=null,
    private var newsCategoryEntryService: NewsCategoryEntryService?=null,
    private var pageService: PageService?=null
) {

    private fun generateLanguageSettingsFile():Languages{
        val objectMapper = ObjectMapper()
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true)

        val languages = Languages(languageService!!.getAllLanguages())
        loggerService!!.logOnConsole(languages.toString())
        val settingsFile = FileUtils.getLanguageSettingsFile()
        loggerService!!.logOnConsole(settingsFile.absolutePath)

        objectMapper.writeValue(settingsFile, languages)
        return languages
    }

    private fun generateCountrySettingsFile():Countries{
        val objectMapper = ObjectMapper()
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true)

        val countries = Countries(countryService!!.getAllCountries())
        loggerService!!.logOnConsole(countries.toString())
        val settingsFile = FileUtils.getCountrySettingsFile()
        loggerService!!.logOnConsole(settingsFile.absolutePath)

        objectMapper.writeValue(settingsFile, countries)
        return countries
    }

    private fun generateNewspaperSettingsFile():Newspapers{
        val objectMapper = ObjectMapper()
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true)

        val newspapers = Newspapers(newsPaperService!!.getAllNewsPapers())
        loggerService!!.logOnConsole(newspapers.toString())
        val settingsFile = FileUtils.getNewspaperSettingsFile()
        loggerService!!.logOnConsole(settingsFile.absolutePath)

        objectMapper.writeValue(settingsFile, newspapers)
        return newspapers
    }

    private fun generatePageSettingsFile():Pages{
        val objectMapper = ObjectMapper()
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true)

        val pages = Pages(pageService!!.getAllPages())
        loggerService!!.logOnConsole(pages.toString())
        val settingsFile = FileUtils.getPageSettingsFile()
        loggerService!!.logOnConsole(settingsFile.absolutePath)

        objectMapper.writeValue(settingsFile, pages)
        return pages
    }

    private fun generateNewsCategorySettingsFile():NewsCategories{
        val objectMapper = ObjectMapper()
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true)

        val newsCategories = NewsCategories(newsCategoryService!!.getAllNewsCategories())
        loggerService!!.logOnConsole(newsCategories.toString())
        val settingsFile = FileUtils.getNewsCategorySettingsFile()
        loggerService!!.logOnConsole(settingsFile.absolutePath)

        objectMapper.writeValue(settingsFile, newsCategories)
        return newsCategories
    }

    private fun generateNewsCategoryEntriesSettingsFile():NewsCategoryEntries{
        val objectMapper = ObjectMapper()
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true)

        val newsCategoryEntries = NewsCategoryEntries(newsCategoryEntryService!!.getAllNewsCategoryEntries())
        loggerService!!.logOnConsole(newsCategoryEntries.toString())
        val settingsFile = FileUtils.getNewsCategoryEntriesSettingsFile()
        loggerService!!.logOnConsole(settingsFile.absolutePath)

        objectMapper.writeValue(settingsFile, newsCategoryEntries)
        return newsCategoryEntries
    }

    fun generateSettingsData(){
        generateLanguageSettingsFile()
        generateCountrySettingsFile()
        generateNewspaperSettingsFile()
        generatePageSettingsFile()
        generateNewsCategorySettingsFile()
        generateNewsCategoryEntriesSettingsFile()
    }
}