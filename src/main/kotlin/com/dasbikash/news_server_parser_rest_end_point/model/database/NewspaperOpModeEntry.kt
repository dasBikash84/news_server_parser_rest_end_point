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
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlTransient

@Entity
@Table(name = DatabaseTableNames.NEWS_PAPER_OP_MODE_ENTRY_NAME)
@XmlRootElement
data class NewspaperOpModeEntry(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        @Column(columnDefinition = "enum('RUNNING','GET_SYNCED')")
        @Enumerated(EnumType.STRING)
        private var opMode: ParserMode? = null,

        @ManyToOne(targetEntity = Newspaper::class, fetch = FetchType.EAGER)
        @JoinColumn(name = "newsPaperId")
        private var newspaper: Newspaper? = null,
        @UpdateTimestamp
        var created: Date? = null
):NsParserRestDbEntity {
    @JsonProperty
    @XmlElement
    @Transient
    fun getNewsPapername(): String? {
        return newspaper?.name
    }
    @JsonProperty
    @XmlElement
    @Transient
    fun getChangedOpMode(): String? {
        return opMode?.name
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
    fun getOpMode(): ParserMode? {
        return opMode
    }

    fun setOpMode(opMode: ParserMode?) {
        this.opMode = opMode
    }
}