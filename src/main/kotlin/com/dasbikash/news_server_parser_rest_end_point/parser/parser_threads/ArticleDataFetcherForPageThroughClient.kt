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
import com.dasbikash.news_server_parser_rest_end_point.model.database.*
import com.dasbikash.news_server_parser_rest_end_point.parser.ArticleBodyParser
import com.dasbikash.news_server_parser_rest_end_point.parser.PreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.firebase.FireStoreDataUtils
import com.dasbikash.news_server_parser_rest_end_point.services.*
import com.dasbikash.news_server_parser_rest_end_point.utills.LoggerService
import com.dasbikash.news_server_parser_rest_end_point.utills.RxJavaUtils
import java.util.*

class ArticleDataFetcherForPageThroughClient(
        pageService: PageService,
        articleService: ArticleService,
        loggerService: LoggerService,
        rxJavaUtils: RxJavaUtils,
        newsPaperService: NewsPaperService,
        pageParsingIntervalService: PageParsingIntervalService,
        pageParsingHistoryService: PageParsingHistoryService,
        val parserExceptionHandlerService: ParserExceptionHandlerService,
        val pageDownloadRequestEntryService: PageDownloadRequestEntryService)
    : ArticleDataFetcherBase(
        ParserMode.PARSE_THROUGH_CLIENT,pageService, articleService, loggerService,
        rxJavaUtils, newsPaperService, pageParsingIntervalService, pageParsingHistoryService) {

    init {
        FireStoreDataUtils.getInstance(loggerService,pageDownloadRequestEntryService)
    }

    override fun doParsingForPage(currentPage: Page) {

        val opMode = newsPaperService.getLatestOpModeEntryForNewspaper(currentPage.getNewspaper()!!)

        if (opMode.getOpMode() != ParserMode.PARSE_THROUGH_CLIENT) {
            return
        }


        val activePageDownloadRequestEntries =
                pageDownloadRequestEntryService.findActivePageDownloadRequestEntryForPage(currentPage)

        if (activePageDownloadRequestEntries.isNotEmpty()) {

            if (activePageDownloadRequestEntries.filter { it.pageDownloadRequestMode == PageDownloadRequestMode.ARTICLE_PREVIEW_PAGE }.count() == 1) {

                val articlePreviewPageDownloadRequestEntry =
                        activePageDownloadRequestEntries.find { it.pageDownloadRequestMode == PageDownloadRequestMode.ARTICLE_PREVIEW_PAGE }!!

                if (articlePreviewPageDownloadRequestEntry.responseContent != null) {

                    val currentPageNumber = getCurrentPageNumber(currentPage)
                    val articleList: MutableList<Article> = mutableListOf()

                    var parsingResult: Pair<MutableList<Article>, String>? = null
                    try {
                        parsingResult = PreviewPageParser
                                .parsePreviewPageForArticles(currentPage, currentPageNumber,
                                        articlePreviewPageDownloadRequestEntry.getResponseContentAsString()!!)
                        articleList.addAll(parsingResult.first)
                    } catch (e: NewsPaperNotFoundForPageException) {
                        loggerService.logOnConsole("${e::class.java.simpleName} for page: ${currentPage.name} Np: ${currentPage.getNewspaper()?.name}")
                        parserExceptionHandlerService.handleException(e)
                        return
                    } catch (e: ParserNotFoundException) {
                        loggerService.logOnConsole("${e::class.java.simpleName} for page: ${currentPage.name} Np: ${currentPage.getNewspaper()?.name}")
                        parserExceptionHandlerService.handleException(e)
                        return
                    } catch (e: Throwable) {
                        loggerService.logOnConsole("${e::class.java.simpleName} for page: ${currentPage.name} Np: ${currentPage.getNewspaper()?.name}")
                        when (e) {
                            is ParserException -> parserExceptionHandlerService.handleException(e)
                            else -> parserExceptionHandlerService.handleException(ParserException(e))
                        }
                        deactivatePageDownloadRequestEntry(articlePreviewPageDownloadRequestEntry)
                        emptyPageAction(currentPage, parsingResult?.second ?: "")
                        return
                    }

                    val parsableArticleList =
                            articleList.asSequence()
                                    .filter {
                                        articleService.findArticleById(it.articleId!!) == null &&
                                                articleService.findArticleById(Article.getStripedArticleId(it.articleId!!)) == null
                                    }
                                    .toMutableList()

                    if (parsableArticleList.size == 0) {
                        allArticleRepeatAction(currentPage, parsingResult?.second ?: "")
                    } else {
                        parsableArticleList.asSequence().forEach {
                            articleService.save(it)
                            pageDownloadRequestEntryService.addArticleBodyDownloadRequestEntryForPage(currentPage,it)
                        }
                    }
                    deactivatePageDownloadRequestEntry(articlePreviewPageDownloadRequestEntry)
                }
            } else {
                var processedArticleCount = 0
                var newArticleCount = 0
                activePageDownloadRequestEntries
                        .filter { it.pageDownloadRequestMode == PageDownloadRequestMode.ARTICLE_BODY && it.responseContent != null }
                        .asSequence().forEach {
                            var article: Article? = null
                            try {
                                article = articleService.findArticleById(it.responseDocumentId!!)!!
                                ArticleBodyParser.getArticleBody(article, it.getResponseContentAsString()!!)
                            } catch (ex: ParserException) {
                                ex.printStackTrace()
                                parserExceptionHandlerService.handleException(ex)
                            } catch (ex: Throwable) {
                                parserExceptionHandlerService.handleException(ParserException(ex))
                            }
                            article?.let {
                                if (article.isDownloaded()) {
                                    if (article.previewImageLink == null && article.imageLinkList.size > 0) {
                                        try {
                                            article.previewImageLink = article.imageLinkList.first { it.link != null }.link
                                        } catch (ex: Throwable) {
                                        }
                                    }
                                    if (article.getPublicationTS() !=null && article.getPublicationTS()!! > Date()){
                                        article.setPublicationTS(Date())
                                    }
                                    if (article.getModificationTS() !=null && article.getModificationTS()!! > Date()){
                                        article.setModificationTS(Date())
                                    }
                                    articleService.save(article)
                                        newArticleCount++
                                }
                            }
                            processedArticleCount++
                            deactivatePageDownloadRequestEntry(it)
                        }

                if (activePageDownloadRequestEntries.filter { it.pageDownloadRequestMode == PageDownloadRequestMode.ARTICLE_BODY }.count() ==
                        processedArticleCount) {
                    savePageParsingHistory(currentPage, getCurrentPageNumber(currentPage), newArticleCount, "")
                }
            }
        } else {
            if (goForPageParsing(currentPage)) {

                val currentPageNumber = getCurrentPageNumber(currentPage)
                //place preview page download request.
                val pageLink = PreviewPageParser.getPageLinkByPageNumber(currentPage, currentPageNumber)
                if (pageLink != null) {
                    pageDownloadRequestEntryService.addArticlePreviewPageDownloadRequestEntryForPage(currentPage, pageLink)
                } else {
                    emptyPageAction(currentPage, "")
                }
            }
        }
    }

    private fun getCurrentPageNumber(currentPage: Page): Int {
        val currentPageNumber: Int

        if (currentPage.isPaginated()) {
            currentPageNumber = getLastParsedPageNumber(currentPage) + 1
        } else {
            currentPageNumber = PAGE_NUMBER_NOT_APPLICABLE
        }
        return currentPageNumber
    }

    private fun deactivatePageDownloadRequestEntry(articlePreviewPagepageDownloadRequestEntry: PageDownloadRequestEntry) {
        pageDownloadRequestEntryService.delete(articlePreviewPagepageDownloadRequestEntry)
    }
}