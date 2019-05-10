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
import javax.persistence.*

@Entity
@Table(name = DatabaseTableNames.PAGE_GROUP_TABLE_NAME)
data class PageGroup(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int?=null,
        var name: String?=null
): NsParserRestDbEntity
{
    @OneToMany(fetch = FetchType.LAZY,targetEntity = Page::class)
    @JoinTable(
            name="page_group_entries",
            joinColumns = arrayOf(JoinColumn(name = "pageGroupId")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "pageId"))
    )
    @JsonIgnore
    var pageList: List<Page>?=null
    @Transient
    private var pageIdList: List<String>?=null
    fun getPageIdList():List<String>{
        return pageList?.asSequence()?.map { it.id }?.toList() ?: emptyList()
    }
}