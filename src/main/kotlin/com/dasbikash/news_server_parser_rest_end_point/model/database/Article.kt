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

import com.dasbikash.news_server_parser_rest_end_point.model.ArticleImage
import com.dasbikash.news_server_parser_rest_end_point.utills.HashUtils
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.*
import java.util.*
//import javax.persistence.*
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlTransient
import kotlin.collections.ArrayList

//@Entity
//@Table(name = DatabaseTableNames.ARTICLE_TABLE_NAME)
@XmlRootElement
data class Article(
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private var serial:Int?=null,

//        @Column(name = "id")
        @Id
        @JsonProperty(value = "id")
        var articleId: String? = null,

//        @ManyToOne(targetEntity = Page::class, fetch = FetchType.EAGER)
//        @JoinColumn(name = "pageId")
        private var page: Page? = null,

        var title: String? = null,
        private var modificationTS: Date? = null,
        private var publicationTS: Date? = null,

//        @Column(columnDefinition = "text")
        var articleText: String? = null,

//        @ElementCollection(targetClass = ArticleImage::class,fetch = FetchType.EAGER)
//        @CollectionTable(name = "image_links", joinColumns = [JoinColumn(name = "articleId")])
//        @Column(name = "imageLink", columnDefinition = "text")
        var imageLinkList: List<ArticleImage> = ArrayList(),

//        @Column(columnDefinition = "text")
        var previewImageLink: String? = null,

//        @Column(columnDefinition = "text")
        var articleLink: String? = null,

//        @Temporal(TemporalType.TIMESTAMP)
//        @Column(name = "modified", nullable = false, updatable = false,insertable = false)
//        var modified: Date? = null
        var modified: Long = System.nanoTime()
) : NsParserRestDbEntity {

    @JsonIgnore
    @XmlTransient
    fun getPublicationTS():Date?{
        return publicationTS
    }
    fun setPublicationTS(publicationTS: Date?){
        this.publicationTS = publicationTS
    }

    @JsonIgnore
    @XmlTransient
    fun getModificationTS():Date?{
        return modificationTS
    }
    fun setModificationTS(modificationTS: Date?){
        this.modificationTS = modificationTS
    }

//    @JsonIgnore
//    @XmlTransient
//    fun getSerial():Int?{
//        return serial
//    }
//    fun setSerial(serial: Int?){
//        this.serial=serial
//    }

    @JsonIgnore
    @XmlTransient
    fun getPage():Page?{
        return page
    }
    fun setPage(page: Page?){
        this.page=page
    }

    @JsonProperty
    @XmlElement
    @Transient
    fun getPageId(): String? {
        return page?.id
    }

    @JsonProperty
    @XmlElement
    @Transient
    fun getPublicationTime(): Date {
        var publicationTime = Calendar.getInstance()
        this.page?.getNewspaper()?.getCountry()?.let {
            val timezone: TimeZone = TimeZone.getTimeZone(this.page?.getNewspaper()?.getCountry()?.timeZone)
            publicationTime = Calendar.getInstance(timezone)
        }
        if (publicationTS != null) {
            publicationTime.time = this.publicationTS
        } else{
            publicationTime.time = this.modificationTS
        }
        return publicationTime.time
    }

    override fun toString(): String {
        return "Article(id='$articleId', page=${page?.name}, title=$title, modificationTS=$modificationTS, publicationTS=$publicationTS, " +
                "articleText=${articleText ?: ""}, imageLinkList=$imageLinkList, previewImageLink=$previewImageLink)"
    }
    @Transient
    @JsonIgnore
    @XmlTransient
    fun isDownloaded(): Boolean {
        return articleText != null
    }

    fun setModificationTs(modificationTS: Long) {
        val calander = Calendar.getInstance()
        calander.timeInMillis = modificationTS
        this.modificationTS = calander.time
    }
    companion object{
        const val MODIFIED_PROPERTY_NAME = "modified"

        fun getArticleIdFromArticleLink(articleLink: String,page: Page):String{
            val articleIdBuilder = StringBuilder(HashUtils.hash(articleLink)).append("_")

            if (page.isTopLevelPage()) {
                articleIdBuilder.append(page.id)
            } else {
                articleIdBuilder.append(page.parentPageId)
            }
            return articleIdBuilder.toString()
        }

        fun getStripedArticleId(articleId: String):String{
            return articleId.split(Regex("_")).get(0)
        }
    }
}