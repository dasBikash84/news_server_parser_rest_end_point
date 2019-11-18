package com.dasbikash.news_server_parser_rest_end_point.Init

import com.dasbikash.news_server_parser_rest_end_point.model.database.NewsCategory
import com.dasbikash.news_server_parser_rest_end_point.model.database.NewsCategoryEntry
import com.dasbikash.news_server_parser_rest_end_point.model.database.Newspaper
import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.repositories.*
import com.dasbikash.news_server_parser_rest_end_point.utills.FileReaderUtils
import com.dasbikash.news_server_parser_rest_end_point.utills.LoggerUtils
import org.springframework.stereotype.Service

@Service
open class SettingsBootstrapService(
        private var languageRepository: LanguageRepository?=null,
        private var countryRepository: CountryRepository?=null,
        private var newspaperRepository: NewspaperRepository?=null,
        private var pageRepository: PageRepository?=null,
        private var newsCategoryRepository: NewsCategoryRepository?=null,
        private var newsCategoryEntryRepository: NewsCategoryEntryRepository?=null
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
        if (newsCategoryEntryRepository!!.count() == 0L) {
            LoggerUtils.logOnConsole("Loading News-category Entry data.")
            val pages = pageRepository!!.findAll().toList()
            val newCategories = newsCategoryRepository!!.findAll().toList()

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
            newsCategoryEntryRepository!!.saveAll(newsCategoryEntries)
        }
    }

    private fun loadNewsCategories() {
        if (newsCategoryRepository!!.count() == 0L) {
            LoggerUtils.logOnConsole("Loading News-category data.")

            val newCategories = mutableListOf<NewsCategory>()

            FileReaderUtils
                    .jsonFileToEntityList(NEWS_CATEGORY_DATA_FILE_PATH, NewsCategories::class.java)
                    .newsCategories!!.asSequence().forEach {
                newCategories.add(it)
            }
            newsCategoryRepository!!.saveAll(newCategories)
        }
    }

    private fun loadPages() {
        if (pageRepository!!.count() == 0L) {
            LoggerUtils.logOnConsole("Loading page data.")

            val newspapers = newspaperRepository!!.findAll().toList()
            val pages = mutableListOf<Page>()

            FileReaderUtils
                    .jsonFileToEntityList(PAGE_DATA_FILE_PATH, Pages::class.java)
                    .pages!!.asSequence().forEach {
                it.setNewspaper(newspapers)
                pages.add(it)
            }
            pageRepository!!.saveAll(pages)
        }
    }

    private fun loadNewspapers() {
        if (newspaperRepository!!.count() == 0L) {
            LoggerUtils.logOnConsole("Loading newspaper data.")

            val languages = languageRepository!!.findAll().toList()
            val countries = countryRepository!!.findAll().toList()

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
            newspaperRepository!!.saveAll(newspapers)
        }
    }

    private fun loadCountries() {
        if (countryRepository!!.count() == 0L) {
            LoggerUtils.logOnConsole("Loading country data.")
            FileReaderUtils
                    .jsonFileToEntityList(COUNTRY_DATA_FILE_PATH, Countries::class.java)
                    .countries!!.forEach {
                countryRepository!!.save(it)
            }
        }
    }

    private fun loadLanguages() {
        if (languageRepository!!.count() == 0L) {
            LoggerUtils.logOnConsole("Loading language data.")
            FileReaderUtils
                    .jsonFileToEntityList(LANGUAGE_DATA_FILE_PATH, Languages::class.java)
                    .languages!!.forEach {
                languageRepository!!.save(it)
            }
        }
    }

    companion object{
        private val LANGUAGE_DATA_FILE_PATH = "/language_data.json"
        private val COUNTRY_DATA_FILE_PATH = "/country_data.json"
        private val NEWSPAPER_DATA_FILE_PATH = "/newspaper_data.json"
        private val PAGE_DATA_FILE_PATH = "/page_data_full.json"
        private val NEWS_CATEGORY_DATA_FILE_PATH = "/news_category_data.json"
        private val NEWS_CATEGORY_ENTRY_DATA_FILE_PATH = "/news_category_entry_data.json"
    }
}