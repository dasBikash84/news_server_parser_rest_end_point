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

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import javax.persistence.*
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlTransient

@Entity
@Table(name = DatabaseTableNames.PAGE_TABLE_NAME)
@XmlRootElement
data class Page(
        @Id
        var id: String="",

        @ManyToOne(targetEntity = Newspaper::class,fetch = FetchType.EAGER)
        @JoinColumn(name="newsPaperId")
        private var newspaper: Newspaper?=null,

        var parentPageId: String?=null,
        var name: String?=null,

        @Column(name="linkFormat", columnDefinition="text")
        private var linkFormat:String? = null,

        private var active: Boolean = true,
        var weekly: Boolean = true,

        @OneToMany(fetch = FetchType.LAZY,mappedBy = "page",targetEntity = Article::class)
        private var articleList: List<Article>?=null,

        @Transient
        var hasChild:Boolean = false,

        @JsonIgnore
        @XmlTransient
        var linkVariablePartFormat:String? = DEFAULT_LINK_TRAILING_FORMAT,
        @JsonIgnore
        @XmlTransient
        var firstEditionDateString:String? = null,
        @JsonIgnore
        @XmlTransient
        var weeklyPublicationDay:Int? = 0,

        @JsonIgnore
        @XmlTransient
        @OneToMany(fetch = FetchType.LAZY,mappedBy = "page",targetEntity = PageParsingHistory::class)
        var pageParsingHistory: List<PageParsingHistory>?=null

): NsParserRestDbEntity {
    companion object {
        @JvmField
        val TOP_LEVEL_PAGE_PARENT_ID = "PAGE_ID_0"
        @JvmField
        val DEFAULT_LINK_TRAILING_FORMAT = "page_num"
    }

    @Transient
    @JsonProperty
    @XmlElement
    fun isTopLevelPage():Boolean{
        return parentPageId == TOP_LEVEL_PAGE_PARENT_ID
    }

    @Transient
    @JsonProperty
    @XmlElement
    fun getNewsPaperId():String{
        return newspaper?.id ?: ""
    }

    @Transient
    @JsonProperty
    @XmlElement
    fun isHasData():Boolean{
        return linkFormat !=null
    }

    @JsonIgnore
    @XmlTransient
    fun getNewspaper():Newspaper?{
        return newspaper
    }
    fun setNewspaper(newsPaper: Newspaper?){
        this.newspaper=newsPaper
    }

    @JsonIgnore
    @XmlTransient
    fun getArticleList():List<Article>?{
        return articleList
    }
    fun setArticleList(articleList: List<Article>?){
        this.articleList=articleList
    }

//    @JsonIgnore
//    @XmlTransient
    fun getActive(): Boolean {
        return active
    }

    fun setActive(active: Boolean) {
        this.active = active
    }

    @JsonIgnore
    @XmlTransient
    fun getLinkFormat(): String? {
        return linkFormat
    }

    fun setLinkFormat(linkFormat: String?) {
        this.linkFormat = linkFormat
    }

    @Transient
    @SerializedName("newsPaperId")
    @XmlTransient
    var newsPaperIdData:String?=null

    fun setNewspaper(newspapers: List<Newspaper>) {
        newspapers.asSequence().forEach {
            if (it.id == newsPaperIdData){
                newspaper = it
                return@forEach
            }
        }
    }

    override fun toString(): String {
        return "Page(id='$id', newspaper=${newspaper?.name}, parentPageId=$parentPageId, name=$name, active=$active,hasChild = $hasChild)"
    }

    fun isPaginated(): Boolean {
        return linkFormat!=null
    }
}