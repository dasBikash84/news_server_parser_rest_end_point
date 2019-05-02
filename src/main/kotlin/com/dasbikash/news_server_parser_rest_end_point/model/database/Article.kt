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

import com.dasbikash.news_server_parser.model.ArticleImage
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList


@Entity
@Table(name = DatabaseTableNames.ARTICLE_TABLE_NAME)
data class Article(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonIgnore
        var serial:Int?=null,
        @Column(name = "id")
        @JsonProperty(value = "id")
        var articleId: String? = null,

        @ManyToOne(targetEntity = Page::class, fetch = FetchType.EAGER)
        @JoinColumn(name = "pageId")
        @JsonIgnore
        var page: Page? = null,

        var title: String? = null,
        @JsonIgnore
        var modificationTS: Date? = null,
        @JsonIgnore
        var publicationTS: Date? = null,

        @Column(columnDefinition = "text")
        var articleText: String? = null,

        @ElementCollection(targetClass = ArticleImage::class)
        @CollectionTable(name = "image_links", joinColumns = [JoinColumn(name = "articleId")])
        @Column(name = "imageLink", columnDefinition = "text")
        var imageLinkList: List<ArticleImage> = ArrayList(),

        @Column(columnDefinition = "text")
        var previewImageLink: String? = null
) : NsParserRestDbEntity {
    @Transient
    private var pageId: String? = null


    override fun toString(): String {
        return "Article(id='$articleId', page=${page?.name}, title=$title, modificationTS=$modificationTS, publicationTS=$publicationTS, " +
                "articleText=${articleText ?: ""}, imageLinkList=$imageLinkList, previewImageLink=$previewImageLink)"
    }

    @JsonProperty(value = "pageId")
    fun getPageId(): String? {
        return page?.id
    }
    @JsonProperty(value = "publicationTime")
    fun getPublicationTime(): Date {
        var publicationTime = Calendar.getInstance()
        this.page?.newspaper?.country?.let {
            val timezone: TimeZone = TimeZone.getTimeZone(this.page?.newspaper?.country?.timeZone)
            publicationTime = Calendar.getInstance(timezone)
        }
        if (publicationTS != null) {
            publicationTime.time = this.publicationTS
        } else{
            publicationTime.time = this.modificationTS
        }
        return publicationTime.time
    }
}