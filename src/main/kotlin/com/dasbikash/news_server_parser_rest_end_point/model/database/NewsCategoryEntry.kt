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

package com.dasbikash.news_server_parser_rest_end_point.model.database

import com.dasbikash.news_server_parser_rest_end_point.Init.Pages
import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.gson.annotations.SerializedName
import javax.persistence.*
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlTransient

@Entity
@Table(name = DatabaseTableNames.NEWS_CATEGORY_ENTRY_ENTRY_NAME)
@XmlRootElement
data class NewsCategoryEntry(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        @ManyToOne(targetEntity = NewsCategory::class, fetch = FetchType.EAGER)
        @JoinColumn(name = "newsCategoryId")
        private var newsCategory: NewsCategory? = null,

        @ManyToOne(targetEntity = Page::class, fetch = FetchType.EAGER)
        @JoinColumn(name = "pageId")
        private var page: Page? = null
) : NsParserRestDbEntity {
    @Transient
    @XmlElement
    fun getPageId(): String? {
        return page?.id
    }

    @Transient
    @XmlElement
    fun getNewsCategoryId(): String? {
        return newsCategory?.id
    }

    @JsonIgnore
    @XmlTransient
    fun getPage():Page?{
        return page
    }
    fun setPage(page: Page?){
        this.page=page
    }

    @JsonIgnore
    @XmlTransient
    fun getNewsCategory():NewsCategory?{
        return newsCategory
    }

    fun setNewsCategory(newsCategory: NewsCategory?){
        this.newsCategory=newsCategory
    }

    @Transient
    @SerializedName("newsCategoryId")
    @XmlTransient
    var newsCategoryIdData:String?=null

    @Transient
    @SerializedName("pageId")
    @XmlTransient
    var pageIdData:String?=null

    fun setNewsCategoryData(newscategories: List<NewsCategory>){
        newscategories.asSequence().forEach {
            if (it.id == newsCategoryIdData){
                newsCategory = it
                return@forEach
            }
        }
    }

    fun setPageData(pages: List<Page>){
        pages.asSequence().forEach {
            if (it.id == pageIdData){
                page = it
                return@forEach
            }
        }
    }
}