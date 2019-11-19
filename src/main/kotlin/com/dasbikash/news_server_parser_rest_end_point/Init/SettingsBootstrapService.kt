package com.dasbikash.news_server_parser_rest_end_point.Init

import com.dasbikash.news_server_parser_rest_end_point.model.database.NewsCategory
import com.dasbikash.news_server_parser_rest_end_point.model.database.NewsCategoryEntry
import com.dasbikash.news_server_parser_rest_end_point.model.database.Newspaper
import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.services.*
import com.dasbikash.news_server_parser_rest_end_point.utills.FileReaderUtils
import com.dasbikash.news_server_parser_rest_end_point.utills.FileUtils
import com.dasbikash.news_server_parser_rest_end_point.utills.LoggerService
import org.springframework.stereotype.Service

@Service
open class SettingsBootstrapService(
        private var languageService: LanguageService?=null,
        private var countryService: CountryService?=null,
        private var newsPaperService: NewsPaperService?=null,
        private var pageService: PageService?=null,
        private var newsCategoryService: NewsCategoryService?=null,
        private var newsCategoryEntryService: NewsCategoryEntryService?=null,
        private var loggerService: LoggerService?=null
) {
    fun bootstrapSettingsIfRequired(){
        loadCountries()
        loadLanguages()
        loadNewspapers()
        loadPages()
        loadNewsCategories()
        loadNewsCategoryEntries()
    }

    private fun loadNewsCategoryEntries() {
        if (newsCategoryEntryService!!.getCount() == 0L) {
            loggerService!!.logOnConsole("Loading News-category Entry data.")
            val pages = pageService!!.getAllPages()
            val newCategories = newsCategoryService!!.getAllNewsCategories()

            val newsCategoryEntries = mutableListOf<NewsCategoryEntry>()

            FileReaderUtils
                    .jsonFileToEntityList(NEWS_CATEGORY_ENTRY_DATA_FILE_PATH, NewsCategoryEntries::class.java)
                    .newsCategoryEntries!!.asSequence().forEach {
                it.setNewsCategoryData(newCategories)
                it.setPageData(pages)
                if (it.getNewsCategory()!=null && it.getPage()!=null){
                    newsCategoryEntries.add(it)
                }
            }
            newsCategoryEntryService!!.saveAll(newsCategoryEntries)
        }
    }

    private fun loadNewsCategories() {
        if (newsCategoryService!!.getCount() == 0L) {
            loggerService!!.logOnConsole("Loading News-category data.")

            val newCategories = mutableListOf<NewsCategory>()

            FileReaderUtils
                    .jsonFileToEntityList(NEWS_CATEGORY_DATA_FILE_PATH, NewsCategories::class.java)
                    .newsCategories!!.asSequence().forEach {
                newCategories.add(it)
            }
            newsCategoryService!!.saveAll(newCategories)
        }
    }

    private fun loadPages() {
        if (pageService!!.getCount() == 0L) {
            loggerService!!.logOnConsole("Loading page data.")

            val newspapers = newsPaperService!!.getAllNewsPapers()
            val pages = mutableListOf<Page>()

            FileReaderUtils
                    .jsonFileToEntityList(PAGE_DATA_FILE_PATH, Pages::class.java)
                    .pages!!.asSequence().forEach {
                it.setNewspaper(newspapers)
                pages.add(it)
            }
            pageService!!.saveAll(pages)
        }
    }

    private fun loadNewspapers() {
        if (newsPaperService!!.getCount() == 0L) {
            loggerService!!.logOnConsole("Loading newspaper data.")

            val languages = languageService!!.getAllLanguages()
            val countries = countryService!!.getAllCountries()

            val newspapers = mutableListOf<Newspaper>()

            FileReaderUtils
                    .jsonFileToEntityList(NEWSPAPER_DATA_FILE_PATH, Newspapers::class.java)
                    .newspapers!!
                    .asSequence()
                    .forEach {
                        it.setCountryData(countries)
                        it.setLanguageData(languages)
                        newspapers.add(it)
            }
            newsPaperService!!.saveAll(newspapers)
        }
    }

    private fun loadCountries() {
        if (countryService!!.getCount() == 0L) {
            loggerService!!.logOnConsole("Loading country data.")
            FileReaderUtils
                    .jsonFileToEntityList(COUNTRY_DATA_FILE_PATH, Countries::class.java)
                    .countries!!.forEach {
                countryService!!.save(it)
            }
        }
    }

    private fun loadLanguages() {
        if (languageService!!.getCount() == 0L) {
            loggerService!!.logOnConsole("Loading language data.")
            FileReaderUtils
                    .jsonFileToEntityList(LANGUAGE_DATA_FILE_PATH, Languages::class.java)
                    .languages!!.forEach {
                languageService!!.save(it)
            }
        }
    }

    companion object{
        const val LANGUAGE_DATA_FILE_PATH = "/${FileUtils.LANGUAGE_DATA_FILE_PATH}"
        const val COUNTRY_DATA_FILE_PATH = "/${FileUtils.COUNTRY_DATA_FILE_PATH}"
        const val NEWSPAPER_DATA_FILE_PATH = "/${FileUtils.NEWSPAPER_DATA_FILE_PATH}"
        const val PAGE_DATA_FILE_PATH = "/${FileUtils.PAGE_DATA_FILE_PATH}"
        const val NEWS_CATEGORY_DATA_FILE_PATH = "/${FileUtils.NEWS_CATEGORY_DATA_FILE_PATH}"
        const val NEWS_CATEGORY_ENTRY_DATA_FILE_PATH = "/${FileUtils.NEWS_CATEGORY_ENTRY_DATA_FILE_PATH}"
    }
}