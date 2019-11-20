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
//import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.*
import java.util.*
//import javax.persistence.*
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlTransient

//@Entity
//@Table(name = DatabaseTableNames.NEWS_PAPER_OP_MODE_ENTRY_NAME)
@XmlRootElement
data class NewspaperOpModeEntry(
        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonIgnore
        @XmlTransient
        var id: Int? = null,
//        @Column(columnDefinition = "enum('OFF','RUNNING','GET_SYNCED','PARSE_THROUGH_CLIENT')")
//        @Enumerated(EnumType.STRING)
        private var opMode: ParserMode = ParserMode.OFF,

//        @ManyToOne(targetEntity = Newspaper::class, fetch = FetchType.EAGER)
//        @JoinColumn(name = "newsPaperId")
        private var newspaper: Newspaper? = null,
//        @UpdateTimestamp
        @JsonIgnore
        @XmlTransient
//        @Column(name = "created", nullable = false, updatable=false,insertable = false)
        var created: Date? = null
):NsParserRestDbEntity {

    @Transient
    private var newsPaperId:String?=null

    @JsonProperty
    @XmlElement
    @Transient
    fun getNewsPaperId(): String? {
        return newspaper?.id
    }

    fun setNewsPaperId(name:String) {
        newsPaperId = name
    }

    fun setNewspaperData(newspapers: List<Newspaper>) {
        newspapers.asSequence().forEach {
            if (it.id == newsPaperId){
                newspaper = it
                return@forEach
            }
        }
    }

    @Transient
    private var opModeName:String?=null

    @JsonProperty
    @XmlElement
    @Transient
    fun getOpModeName(): String? {
        return opMode.name
    }

    fun setOpModeName(name:String) {
        opModeName = name
    }

    fun setOpModeData() {
        ParserMode.values().asSequence().forEach {
            if (it.name == opModeName){
                opMode = it
                return@forEach
            }
        }
    }

    @JsonIgnore
    @XmlTransient
    fun getNewspaper(): Newspaper? {
        return newspaper
    }

    fun setNewspaper(newsPaper: Newspaper?) {
        this.newspaper = newsPaper
    }

    @JsonIgnore
    @XmlTransient
    fun getOpMode(): ParserMode {
        return opMode
    }

    fun setOpMode(opMode: ParserMode?) {
        this.opMode = opMode ?: ParserMode.OFF
    }
}