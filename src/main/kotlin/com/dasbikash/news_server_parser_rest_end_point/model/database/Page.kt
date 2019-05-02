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
import javax.persistence.*

@Entity
@Table(name = DatabaseTableNames.PAGE_TABLE_NAME)
data class Page(
        @Id
        var id: String="",

        @ManyToOne(targetEntity = Newspaper::class,fetch = FetchType.EAGER)
        @JoinColumn(name="newsPaperId")
        @JsonIgnore
        var newspaper: Newspaper?=null,

        var parentPageId: String?=null,
        var name: String?=null,

        @Column(name="linkFormat", columnDefinition="text")
        @JsonIgnore
        var linkFormat:String? = null,

        @JsonIgnore
        var active: Boolean = true,

        @OneToMany(fetch = FetchType.LAZY,mappedBy = "page",targetEntity = Article::class)
        @JsonIgnore
        var articleList: List<Article>?=null,

        @Transient
        var hasChild:Boolean = false

): NsParserRestDbEntity {
    companion object {
        @JvmField
        val TOP_LEVEL_PAGE_PARENT_ID = "PAGE_ID_0"
        @JvmField
        val DEFAULT_LINK_TRAILING_FORMAT = "page_num"
    }

    @Transient
    @JsonProperty(value = "topLevelPage")
    fun isTopLevelPage():Boolean{
        return parentPageId == TOP_LEVEL_PAGE_PARENT_ID
    }

    @Transient
    @JsonProperty(value = "newsPaperId")
    fun getNewsPaperId():String{
        return newspaper?.id ?: ""
    }

    @Transient
    @JsonProperty(value = "hasData")
    fun hasData():Boolean{
        return linkFormat !=null
    }

    override fun toString(): String {
        return "Page(id='$id', newspaper=${newspaper?.name}, parentPageId=$parentPageId, name=$name, active=$active,hasChild = $hasChild)"
    }


}