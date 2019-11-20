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
import org.springframework.data.annotation.*
//import javax.persistence.*
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlTransient

//@Entity
//@Table(name = DatabaseTableNames.LANGUAGE_TABLE_NAME)
@XmlRootElement
data class Language(
        @Id var id: String = "",
        var name: String? = null,
//        @OneToMany(targetEntity = Newspaper::class, mappedBy = "language", fetch = FetchType.LAZY)
        private var newsPapers: List<Newspaper>? = null
): NsParserRestDbEntity {
    @JsonIgnore
    @XmlTransient
    fun getNewsPapers():List<Newspaper>?{
        return newsPapers
    }
    fun setNewsPapers(newsPapers: List<Newspaper>?){
        this.newsPapers=newsPapers
    }
    override fun toString(): String {
        return "Language(id='$id', name=$name)"
    }
}