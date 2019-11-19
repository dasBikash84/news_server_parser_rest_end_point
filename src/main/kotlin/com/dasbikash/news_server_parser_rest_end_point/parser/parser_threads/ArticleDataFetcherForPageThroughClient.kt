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

/*import com.dasbikash.news_server_parser.database.DatabaseUtils
import com.dasbikash.news_server_parser.exceptions.NewsPaperNotFoundForPageException
import com.dasbikash.news_server_parser.exceptions.ParserNotFoundException
import com.dasbikash.news_server_parser.exceptions.generic.ParserException
import com.dasbikash.news_server_parser.exceptions.handler.ParserExceptionHandler
import com.dasbikash.news_server_parser_rest_end_point.parser.firebase.FireStoreDataUtils
import com.dasbikash.news_server_parser.model.*
import com.dasbikash.news_server_parser.parser.article_body_parsers.ArticleBodyParser
import com.dasbikash.news_server_parser.parser.preview_page_parsers.PreviewPageParser
import com.dasbikash.news_server_parser.utils.LoggerUtils
import com.dasbikash.news_server_parser.utils.PageDownloadRequestUtils
import org.hibernate.Session*/
import com.dasbikash.news_server_parser_rest_end_point.exceptions.parser_related.handler.ParserExceptionHandlerService
import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.model.database.ParserMode
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
        val parserExceptionHandlerService: ParserExceptionHandlerService)
    : ArticleDataFetcherBase(
        ParserMode.PARSE_THROUGH_CLIENT,pageService, articleService, loggerService,
        rxJavaUtils, newsPaperService, pageParsingIntervalService, pageParsingHistoryService) {

    init {
        FireStoreDataUtils.nop()
    }

    override fun doParsingForPage(currentPage: Page/*, session: Session*/) {

//        val opMode = DatabaseUtils.getOpModeForNewsPaper(session, currentPage.newspaper!!)
        /*val opMode = newsPaperService.getLatestOpModeEntryForNewspaper(currentPage.getNewspaper()!!)

        if (opMode.getOpMode() != ParserMode.PARSE_THROUGH_CLIENT) {
            return
        }
//            LoggerUtils.logOnConsole("Running Parser for page ${currentPage.name} of Np: ${newspaper.name}")

        val activePageDownloadRequestEntries =
                DatabaseUtils.findActivePageDownloadRequestEntryForPage(session, currentPage)
        activePageDownloadRequestEntries.asSequence().forEach { session.refresh(it) }

        if (activePageDownloadRequestEntries.isNotEmpty()) {
//                LoggerUtils.logOnConsole("activePageDownloadRequestEntries.size for page ${currentPage.name} of Np: ${activePageDownloadRequestEntries.size}")

            if (activePageDownloadRequestEntries.filter { it.pageDownloadRequestMode == PageDownloadRequestMode.ARTICLE_PREVIEW_PAGE }.count() == 1) {

                val articlePreviewPageDownloadRequestEntry =
                        activePageDownloadRequestEntries.find { it.pageDownloadRequestMode == PageDownloadRequestMode.ARTICLE_PREVIEW_PAGE }!!

                if (articlePreviewPageDownloadRequestEntry.responseContent != null) {
                    //parse preview page and place article download request if needed
//                        LoggerUtils.logOnConsole("Preview page content found for page ${currentPage.name} of Np: ${newspaper.name}")

                    val currentPageNumber = getCurrentPageNumber(currentPage,session)
                    val articleList: MutableList<Article> = mutableListOf()

                    var parsingResult: Pair<MutableList<Article>, String>? = null
                    try {
                        parsingResult = PreviewPageParser
                                .parsePreviewPageForArticles(currentPage, currentPageNumber,
                                        articlePreviewPageDownloadRequestEntry.getResponseContentAsString()!!)
                        articleList.addAll(parsingResult.first)
                    } catch (e: NewsPaperNotFoundForPageException) {
                        LoggerUtils.logOnConsole("${e::class.java.simpleName} for page: ${currentPage.name} Np: ${currentPage.newspaper?.name}")
                        ParserExceptionHandler.handleException(e)
                        return
                    } catch (e: ParserNotFoundException) {
                        LoggerUtils.logOnConsole("${e::class.java.simpleName} for page: ${currentPage.name} Np: ${currentPage.newspaper?.name}")
                        ParserExceptionHandler.handleException(e)
                        return
                    } catch (e: Throwable) {
                        LoggerUtils.logOnConsole("${e::class.java.simpleName} for page: ${currentPage.name} Np: ${currentPage.newspaper?.name}")
                        when (e) {
                            is ParserException -> ParserExceptionHandler.handleException(e)
                            else -> ParserExceptionHandler.handleException(ParserException(e))
                        }
                        deactivatePageDownloadRequestEntry(articlePreviewPageDownloadRequestEntry,session)
                        emptyPageAction(currentPage, parsingResult?.second ?: "",session=session)
                        return
                    }
//                        LoggerUtils.logOnConsole("${articleList.size} article preview found for page ${currentPage.name} of Np: ${newspaper.name}")

                    val parsableArticleList =
                            articleList.asSequence()
                                    .filter {
                                        (DatabaseUtils.findArticleById(session, it.id)) == null &&
                                        (DatabaseUtils.findArticleById(session, Article.getStripedArticleId(it.id))) == null
                                    }
                                    .toMutableList()
                    //For Full repeat
//                        LoggerUtils.logOnConsole("${parsableArticleList.size} new article preview found for page ${currentPage.name} of Np: ${newspaper.name}")


                    if (parsableArticleList.size == 0) {
                        allArticleRepeatAction(currentPage, parsingResult?.second ?: "",session)
                    } else {
                        parsableArticleList.asSequence().forEach {
                            DatabaseUtils.runDbTransection(session) {
                                session.save(it)
//                                    LoggerUtils.logOnConsole("New article saved: ${it.id}")
                            }
                            PageDownloadRequestUtils
                                    .addArticleBodyDownloadRequestEntryForPage(session, currentPage, it)
                        }
                    }
                    deactivatePageDownloadRequestEntry(articlePreviewPageDownloadRequestEntry,session)
                } else {
//                        LoggerUtils.logOnConsole("No Preview page content for page ${currentPage.name} of Np: ${newspaper.name}")
                }
            } else {
//                    LoggerUtils.logOnConsole("activePageDownloadRequestEntries.size for article for page ${currentPage.name} of Np: ${activePageDownloadRequestEntries.size}")
                var processedArticleCount = 0
                var newArticleCount = 0
                activePageDownloadRequestEntries
                        .filter { it.pageDownloadRequestMode == PageDownloadRequestMode.ARTICLE_BODY && it.responseContent != null }
                        .asSequence().forEach {
                            //parse and save articles
//                                LoggerUtils.logOnConsole(it.toString())
                            var article: Article? = null
                            try {
                                article = DatabaseUtils.findArticleById(session, it.responseDocumentId!!)!!
                                ArticleBodyParser.getArticleBody(article, it.getResponseContentAsString()!!)
                            } catch (ex: ParserException) {
                                ex.printStackTrace()
                                ParserExceptionHandler.handleException(ex)
                            } catch (ex: Throwable) {
                                ParserExceptionHandler.handleException(ParserException(ex))
                            }
                            article?.let {
                                if (article.isDownloaded()) {
                                    if (article.previewImageLink == null && article.imageLinkList.size > 0) {
                                        try {
                                            article.previewImageLink = article.imageLinkList.first { it.link != null }.link
                                        } catch (ex: Throwable) {
                                        }
                                    }
                                    if (article.publicationTS !=null && article.publicationTS!! > Date()){
                                        article.publicationTS = Date()
                                    }
                                    if (article.modificationTS !=null && article.modificationTS!! > Date()){
                                        article.modificationTS = Date()
                                    }
                                    DatabaseUtils.runDbTransection(session) {
                                        session.update(article)
//                                            LoggerUtils.logOnConsole("New article body saved for article with id: ${it.id}")
                                        newArticleCount++
                                    }
                                }
                            }
                            processedArticleCount++
                            deactivatePageDownloadRequestEntry(it,session)
                        }

                if (activePageDownloadRequestEntries.filter { it.pageDownloadRequestMode == PageDownloadRequestMode.ARTICLE_BODY }.count() ==
                        processedArticleCount) {
                    savePageParsingHistory(currentPage, getCurrentPageNumber(currentPage,session), newArticleCount, "",session = session)
                }
            }
        } else {
            if (goForPageParsing(currentPage,session)) {

                val currentPageNumber = getCurrentPageNumber(currentPage,session)
                //place preview page download request.
                val pageLink = PreviewPageParser.getPageLinkByPageNumber(currentPage, currentPageNumber)
                if (pageLink != null) {
                    PageDownloadRequestUtils.addArticlePreviewPageDownloadRequestEntryForPage(session, currentPage, pageLink)
                } else {
                    emptyPageAction(currentPage, "",session = session)
                }
            }
        }*/
    }

    /*private fun getCurrentPageNumber(currentPage: Page,session: Session): Int {
        val currentPageNumber: Int

        if (currentPage.isPaginated()) {
            currentPageNumber = getLastParsedPageNumber(currentPage,session) + 1
        } else {
            currentPageNumber = PAGE_NUMBER_NOT_APPLICABLE
        }
        return currentPageNumber
    }

    private fun deactivatePageDownloadRequestEntry(articlePreviewPagepageDownloadRequestEntry: PageDownloadRequestEntry,session: Session) {
        DatabaseUtils.runDbTransection(session) {
            session.delete(articlePreviewPagepageDownloadRequestEntry)
        }
    }*/
}