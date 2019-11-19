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

import com.dasbikash.news_server_parser_rest_end_point.exceptions.parser_related.NewsPaperNotFoundForPageException
import com.dasbikash.news_server_parser_rest_end_point.exceptions.parser_related.ParserNotFoundException
import com.dasbikash.news_server_parser_rest_end_point.exceptions.parser_related.generic.ParserException
import com.dasbikash.news_server_parser_rest_end_point.exceptions.parser_related.handler.ParserExceptionHandlerService
import com.dasbikash.news_server_parser_rest_end_point.model.database.Article
import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.model.database.ParserMode
import com.dasbikash.news_server_parser_rest_end_point.parser.ArticleBodyParser
import com.dasbikash.news_server_parser_rest_end_point.parser.PreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.services.*
import com.dasbikash.news_server_parser_rest_end_point.utills.LoggerService
import com.dasbikash.news_server_parser_rest_end_point.utills.RxJavaService
import java.util.*

class ArticleDataFetcherForPageSelf(
        pageService: PageService,
        articleService: ArticleService,
        loggerService: LoggerService,
        rxJavaService: RxJavaService,
        newsPaperService: NewsPaperService,
        pageParsingIntervalService: PageParsingIntervalService,
        pageParsingHistoryService: PageParsingHistoryService,
        val parserExceptionHandlerService: ParserExceptionHandlerService)
    : ArticleDataFetcherBase(
        ParserMode.RUNNING, pageService, articleService, loggerService,
        rxJavaService, newsPaperService, pageParsingIntervalService, pageParsingHistoryService) {

    override fun doParsingForPage(currentPage: Page) {

        val opMode = newsPaperService.getLatestOpModeEntryForNewspaper(currentPage.getNewspaper()!!)

        if (opMode.getOpMode() == ParserMode.PARSE_THROUGH_CLIENT || opMode.getOpMode() == ParserMode.OFF) {
            return
        }

        if (opMode.getOpMode() != ParserMode.GET_SYNCED && !goForPageParsing(currentPage)) {
            return
        }

        try {
            waitForFareNetworkUsage(currentPage)
        } catch (ex: InterruptedException) {
            throw ex
        }

        val currentPageNumber: Int

        if (currentPage.isPaginated()) {
            currentPageNumber = getLastParsedPageNumber(currentPage) + 1
        } else {
            currentPageNumber = PAGE_NUMBER_NOT_APPLICABLE
        }

        val articleList: MutableList<Article> = mutableListOf()

        var parsingResult: Pair<MutableList<Article>, String>? = null
        try {
            parsingResult = PreviewPageParser.parsePreviewPageForArticles(currentPage, currentPageNumber)
            articleList.addAll(parsingResult.first)
        } catch (e: NewsPaperNotFoundForPageException) {
            loggerService.logOnConsole("${e::class.java.simpleName} for page: ${currentPage.name} Np: ${currentPage.getNewspaper()?.name}")
            parserExceptionHandlerService.handleException(e)
            throw InterruptedException()
        } catch (e: ParserNotFoundException) {
            loggerService.logOnConsole("${e::class.java.simpleName} for page: ${currentPage.name} Np: ${currentPage.getNewspaper()?.name}")
            parserExceptionHandlerService.handleException(e)
            throw InterruptedException()
        } catch (e: Throwable) {
            loggerService.logOnConsole("${e::class.java.simpleName} for page: ${currentPage.name} Np: ${currentPage.getNewspaper()?.name}")
            when (e) {
                is ParserException -> {
                    parserExceptionHandlerService.handleException(e)
                }
                else -> parserExceptionHandlerService.handleException(ParserException(e))
            }
            if (opMode.getOpMode() != ParserMode.GET_SYNCED) {
                emptyPageAction(currentPage, parsingResult?.second ?: "")
            }
            return
        }

        val parsableArticleList =
                articleList
                        .asSequence()
                        .filter {
                            articleService.findArticleById(it.articleId!!) == null &&
                                    articleService.findArticleById(Article.getStripedArticleId(it.articleId!!)) == null
                        }
                        .toMutableList()
        //For Full repeat
        if (opMode.getOpMode() != ParserMode.GET_SYNCED && parsableArticleList.size == 0) {
            allArticleRepeatAction(currentPage, parsingResult?.second ?: "")
            return
        }

        //Now go for article data fetching

        var newArticleCount = 0

        for (article in parsableArticleList) {
            try {
                waitForFareNetworkUsage(currentPage)
            } catch (ex: InterruptedException) {
                throw ex
            }
            try {
                ArticleBodyParser.getArticleBody(article)
            } catch (ex: ParserException) {
                parserExceptionHandlerService.handleException(ex)
            } catch (ex: Throwable) {
                parserExceptionHandlerService.handleException(ParserException(ex))
            }
            if (article.isDownloaded()) {
                if (article.previewImageLink == null && article.imageLinkList.size > 0) {
                    try {
                        article.previewImageLink = article.imageLinkList.first { it.link != null }.link
                    } catch (ex: Throwable) {
                    }
                }
                if (article.getPublicationTS() != null && article.getPublicationTS()!! > Date()) {
                    article.setPublicationTS(Date())
                }
                if (article.getModificationTS() != null && article.getModificationTS()!! > Date()) {
                    article.setModificationTS(Date())
                }
                articleService.save(article)
                newArticleCount++
            }
        }

        savePageParsingHistory(
                currentPage, currentPageNumber, newArticleCount, parsingResult?.second ?: "")
    }
}