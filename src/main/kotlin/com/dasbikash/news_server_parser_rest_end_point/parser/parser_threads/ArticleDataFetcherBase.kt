/*
 * Copyright 2019 das.bikash.dev@gmail.com. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dasbikash.news_server_parser_rest_end_point.parser.parser_threads

import com.dasbikash.news_server_parser_rest_end_point.model.database.*
import com.dasbikash.news_server_parser_rest_end_point.services.*
import com.dasbikash.news_server_parser_rest_end_point.utills.LoggerService
import com.dasbikash.news_server_parser_rest_end_point.utills.RxJavaService
import kotlin.random.Random

abstract class ArticleDataFetcherBase constructor(
        opMode: ParserMode,
        val pageService: PageService,
        val articleService: ArticleService,
        val loggerService: LoggerService,
        val rxJavaService: RxJavaService,
        val newsPaperService: NewsPaperService,
        val pageParsingIntervalService: PageParsingIntervalService,
        val pageParsingHistoryService: PageParsingHistoryService
        ) : Thread() {

    private val isSelfParser: Boolean

    init {
        isSelfParser = ((opMode == ParserMode.GET_SYNCED) || (opMode == ParserMode.RUNNING))
    }

    private var lastNetworkRequestTSMap = mutableMapOf<String, Long>()
    private val MIN_START_DELAY = 1 * 1000L //1 secs
    private val MIN_DELAY_BETWEEN_NETWORK_REQUESTS = 5 * 1000L //5 secs
    protected val PAGE_NUMBER_NOT_APPLICABLE = 0

    private val DELEY_BETWEEN_PARSER_LAUNCH_RETRY = 1000L //1 secs
    private val ACTIVE_PARSER_COUNT = 2

    private val activeNewsPapers = mutableListOf<Newspaper>()

    override fun run() {
        loggerService.logOnDb("Starting ${this::class.java.simpleName}")
        sleep(MIN_START_DELAY + Random(System.currentTimeMillis()).nextLong(MIN_START_DELAY))

        do {
            pageService.getAllPages()
                    .filter { it.isTopLevelPage() && !it.active }
                    .forEach {
                        it.active = true
                        pageService.save(it)
                    }

            val pageListForParsing = mutableListOf<Page>()
            val newspapers = getReleventNewsPapers()
            newspapers
                    .flatMap {pageService.getAllPagesByNewspaperId(it.id).filter { it.isHasData() }}
                    .forEach {
                        loggerService.logOnConsole(it.toString())
                        pageListForParsing.add(it)
                    }
            while (pageListForParsing.isNotEmpty()){
                val currentPage = pageListForParsing.shuffled().get(0)
                if(!launchParserForPage(currentPage)){
                    sleep(DELEY_BETWEEN_PARSER_LAUNCH_RETRY)
                }else{
                    pageListForParsing.remove(currentPage)
                }
            }
        } while (newspapers.isNotEmpty())
    }

    private fun launchParserForPage(page:Page):Boolean{
        synchronized(activeNewsPapers){
            if (activeNewsPapers.size >= ACTIVE_PARSER_COUNT ||
                    activeNewsPapers.count { it.id == page.getNewspaper()!!.id }>0){
                return false
            }else{
                activeNewsPapers.add(page.getNewspaper()!!)
                rxJavaService.doTaskInBackGround {
                    parsePage(page,{it -> removeNewsPaperForActiveList(it)})
                }
                return true
            }
        }
    }

    fun removeNewsPaperForActiveList(newpaperId:String){
        synchronized(activeNewsPapers){
            var newspaper:Newspaper? = null
            activeNewsPapers.asSequence().forEach {
                if (it.id == newpaperId){
                    newspaper = it
                    return@forEach
                }
            }
            newspaper?.let {
                activeNewsPapers.remove(it)
            }
        }
    }

    private fun parsePage(page: Page, removeNewsPaperForActiveList: (newspaperId:String)->Unit) {

        pageService.save(page)

        try {
            doParsingForPage(page)

            //Mark pages with articles or if top-level, as active
            if (!page.active) {
                if (page.isTopLevelPage() ||
                        articleService.getArticleCountForPage(page) > 0) {
                    page.active=true
                    pageService.save(page)
                }
            }

            //Recalculate Page parsing intervals if necessary
            var pageParsingInterval = pageParsingIntervalService.getPageParsingIntervalForPage(page)

            if (pageParsingInterval == null) {
                pageParsingInterval = PageParsingInterval.recalculate(articleService, page)
                pageParsingIntervalService.save(pageParsingInterval)
                    loggerService.logOnConsole("PageParsingInterval set to: ${pageParsingInterval.parsingIntervalMS} " +
                            "for page ${page.name} of NP: ${page.getNewspaper()?.name}")
            } else if (pageParsingInterval.needRecalculation()) {
                val newInterval = PageParsingInterval.recalculate(articleService, page)
                if (pageParsingInterval.parsingIntervalMS == newInterval.parsingIntervalMS) {
                    newInterval.parsingIntervalMS = newInterval.parsingIntervalMS!! + 1
                }
                pageParsingInterval.parsingIntervalMS = newInterval.parsingIntervalMS!!
                pageParsingIntervalService.save(pageParsingInterval)
                    loggerService.logOnConsole("PageParsingInterval updated to: ${pageParsingInterval.parsingIntervalMS!! / 1000 / 60} mins " +
                            "for page ${page.name} of NP: ${page.getNewspaper()?.name}")
            }

        } catch (ex: InterruptedException) {
            ex.printStackTrace()
            loggerService.logOnDb("Exiting ${this::class.java.simpleName}")
        } catch (ex: Throwable) {
            ex.printStackTrace()
            loggerService.logError(ex)
        }

        removeNewsPaperForActiveList(page.getNewspaper()!!.id)
    }

    abstract fun doParsingForPage(currentPage: Page)

    private fun getReleventNewsPapers() =
            newsPaperService.getAllNewsPapers()
                    .filter {
                        val opMode = newsPaperService.getLatestOpModeEntryForNewspaper(it)
                        if (isSelfParser) {
                            return@filter ((opMode.getOpMode() == ParserMode.RUNNING) || (opMode.getOpMode() == ParserMode.GET_SYNCED))
                        } else {
                            return@filter opMode.getOpMode() == ParserMode.PARSE_THROUGH_CLIENT
                        }
                    }

    protected fun goForPageParsing(currentPage: Page): Boolean {
        val pageParsingInterval = pageParsingIntervalService.getPageParsingIntervalForPage(currentPage)
        if (pageParsingInterval == null) {
            return true
        }
        val pageParsingHistory = pageParsingHistoryService.getLatestForPage(currentPage)
        if (pageParsingHistory == null) {
            return true
        }
        return (System.currentTimeMillis() - pageParsingHistory.created!!.time) > pageParsingInterval.parsingIntervalMS!!
    }

    protected fun emptyPageAction(currentPage: Page, parsingLogMessage: String, resetOnStart: Boolean = false) {
        savePageParsingHistory(currentPage, 0, 0,
                "No article found." + parsingLogMessage, resetOnStart)
    }

    protected fun allArticleRepeatAction(currentPage: Page, parsingLogMessage: String) {
        savePageParsingHistory(currentPage, 0, 0, "All articles are repeated." + parsingLogMessage)
    }

    protected fun savePageParsingHistory(currentPage: Page, currentPageNumber: Int, articleCount: Int
                                         , parsingLogMessage: String = "", resetOnStart: Boolean = false)
    {
        if (!resetOnStart) {
            if (articleCount > 0) {
                loggerService.logOnConsole("${articleCount} new article found for page: ${currentPage.name} Np: ${currentPage.getNewspaper()?.name}")
            } else {
                loggerService.logOnConsole("No new article found for page: ${currentPage.name} Np: ${currentPage.getNewspaper()?.name}")
            }
        } else {
            loggerService.logOnConsole("Parser reset on start for page: ${currentPage.name} Np: ${currentPage.getNewspaper()?.name}")
        }
        pageParsingHistoryService.save(PageParsingHistory(page = currentPage, pageNumber = currentPageNumber,
                articleCount = articleCount, parsingLogMessage = parsingLogMessage))
    }

    protected fun waitForFareNetworkUsage(page: Page) {

        val lastNetworkRequestTS = lastNetworkRequestTSMap.get(page.getNewspaper()!!.id) ?: 0L

        val ramdomDelay = Random(System.currentTimeMillis()).nextLong(MIN_DELAY_BETWEEN_NETWORK_REQUESTS)

        var delayPeriod = MIN_DELAY_BETWEEN_NETWORK_REQUESTS - (System.currentTimeMillis() - lastNetworkRequestTS)

        if (delayPeriod > 0) {
            delayPeriod += ramdomDelay
        } else {
            delayPeriod = ramdomDelay
        }

        sleep(delayPeriod)
        lastNetworkRequestTSMap.put(page.getNewspaper()!!.id, System.currentTimeMillis())
    }

    protected fun getLastParsedPageNumber(page: Page): Int {
        pageParsingHistoryService.getLatestForPage(page)?.let {
            return it.pageNumber
        }
        return 0
    }
}