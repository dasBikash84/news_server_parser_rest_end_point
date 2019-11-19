package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.model.database.Article
import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.model.database.PageDownloadRequestEntry
import com.dasbikash.news_server_parser_rest_end_point.parser.firebase.RealTimeDbDataUtils
import com.dasbikash.news_server_parser_rest_end_point.repositories.PageDownloadRequestEntryRepository
import org.springframework.stereotype.Service

@Service
open class PageDownloadRequestEntryService
constructor(private var pageDownloadRequestEntryRepository: PageDownloadRequestEntryRepository?=null){

    fun getAllCountries():List<PageDownloadRequestEntry>{
        return pageDownloadRequestEntryRepository!!.findAll()
    }

    fun getCount(): Long {
        return pageDownloadRequestEntryRepository!!.count()
    }

    fun save(pageDownloadRequestEntry: PageDownloadRequestEntry):PageDownloadRequestEntry {
        return pageDownloadRequestEntryRepository!!.save(pageDownloadRequestEntry)
    }

    fun findActivePageDownloadRequestEntryForPage(page: Page): List<PageDownloadRequestEntry> {
        return pageDownloadRequestEntryRepository!!.getAllByPageAndActive(page,true)
    }

    fun delete(articlePreviewPagepageDownloadRequestEntry: PageDownloadRequestEntry) {
        return pageDownloadRequestEntryRepository!!.delete(articlePreviewPagepageDownloadRequestEntry)
    }

    fun addArticlePreviewPageDownloadRequestEntryForPage(page: Page, link: String):Boolean {
        val pageDownloadRequestEntry =
                PageDownloadRequestEntry.getArticlePreviewPageDownloadRequestEntryForPage(page, link)
        return addPageDownloadRequest(pageDownloadRequestEntry)
    }

    fun addArticleBodyDownloadRequestEntryForPage(page: Page, article: Article):Boolean {
        val pageDownloadRequestEntry =
                PageDownloadRequestEntry.getArticleBodyDownloadRequestEntryForPage(page, article.articleLink!!,article)
        return addPageDownloadRequest(pageDownloadRequestEntry)
    }

    private fun addPageDownloadRequest(pageDownloadRequestEntry: PageDownloadRequestEntry):Boolean {
        val documentId = RealTimeDbDataUtils.addPageDownloadRequest(pageDownloadRequestEntry)
        documentId?.let {
            pageDownloadRequestEntry.requestKey = it
            pageDownloadRequestEntryRepository!!.save(pageDownloadRequestEntry)
//            DatabaseUtils.runDbTransection(session) { session.save(pageDownloadRequestEntry) }
            return true
        }
        return false
    }
}