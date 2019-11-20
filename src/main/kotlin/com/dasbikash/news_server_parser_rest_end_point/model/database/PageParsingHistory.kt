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
import org.springframework.data.annotation.*
import java.util.*
//import javax.persistence.*
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlTransient

//@Entity
//@Table(name = DatabaseTableNames.PAGE_PARSING_HISTORY_TABLE_NAME)
@XmlRootElement
data class PageParsingHistory(
        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
//        @ManyToOne(targetEntity = Page::class, fetch = FetchType.LAZY)
//        @JoinColumn(name = "pageId")
        private var page: Page? = null,
        var pageNumber: Int = 0,
        var articleCount: Int = 0,
//        @Column(columnDefinition = "text")
        var parsingLogMessage: String = "",
        var created: Date? = Date()
):NsParserRestDbEntity {
    @Transient
    @JsonProperty
    @XmlElement
    fun getPageId():String?{
        return page?.id
    }
    @JsonIgnore
    @XmlTransient
    fun getPage():Page?{
        return page
    }
    fun setPage(page: Page?){
        this.page=page
    }
    override fun toString(): String {
        return "PageParsingHistory(id=$id, page=${page?.name}, pageNumber=$pageNumber, articleCount=$articleCount, created=$created)"
    }
    companion object{
        fun getEmptyParsingHistoryForPage(page: Page)
                = PageParsingHistory(page = page,pageNumber = 0,articleCount = 0)
    }
}