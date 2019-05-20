package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.DataNotFoundException
import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.repositories.NewspaperRepository
import com.dasbikash.news_server_parser_rest_end_point.repositories.PageRepository
import org.springframework.stereotype.Service

@Service
open class PageService
constructor(open var pageRepository: PageRepository,
            open var newspaperRepository: NewspaperRepository) {

    fun getAllActivePages(): List<Page> {
        val pages =  pageRepository.findAllByActive()
        pages.asSequence().forEach {
            if (it.isTopLevelPage()){
                it.hasChild = pageRepository.findPagesByParentPageIdAndLinkFormatNotNullAndActive(parentPageId = it.id).isNotEmpty()
            }
        }
        return pageRepository.findAllByActive()
    }

    fun getAllActivePagesByNewspaperId(newspaperId: String): List<Page> {
        val newspaperOptional = newspaperRepository.findById(newspaperId)
        if (!newspaperOptional.isPresent){
            throw DataNotFoundException()
        }
        val newspaper = newspaperOptional.get()
        val pages = pageRepository.findPagesByNewspaperAndActive(newspaper)
        pages.asSequence().forEach {
            if (it.isTopLevelPage()){
                it.hasChild = pageRepository.findPagesByParentPageIdAndLinkFormatNotNullAndActive(parentPageId = it.id).isNotEmpty()
            }
        }
        return pageRepository.findPagesByNewspaperAndActive(newspaperOptional.get())
    }
}