package com.dasbikash.news_server_parser_rest_end_point.repositories

import com.dasbikash.news_server_parser_rest_end_point.model.database.Newspaper
import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
//import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.mongodb.repository.MongoRepository

//interface PageRepository : JpaRepository<Page, String>{
interface PageRepository : MongoRepository<Page, String>{

    companion object {
        @JvmStatic val TOP_LEVEL_PAGE_PARENT_ID = "PAGE_ID_0"
    }

    //Find active top level articles for a newspaper
    fun findPagesByNewspaperAndActive(newspaper: Newspaper,
                                      active:Boolean=true):List<Page>
    fun findPagesByNewspaper(newspaper: Newspaper):List<Page>

    fun findAllByActive(active: Boolean = true): List<Page>

    fun findPagesByParentPageIdAndLinkFormatNotNullAndActive(parentPageId: String,active: Boolean=true):List<Page>
}