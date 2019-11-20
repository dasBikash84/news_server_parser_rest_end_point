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
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlTransient

//@Entity
//@Table(name = DatabaseTableNames.NEWSPAPER_TABLE_NAME)
@XmlRootElement
data class Newspaper(
        @Id var id: String="",
        var name: String?=null,

//        @ManyToOne(targetEntity = Country::class,fetch = FetchType.EAGER)
//        @JoinColumn(name="countryName")
        private var country: Country? = null,

//        @ManyToOne(targetEntity = Language::class,fetch = FetchType.EAGER)
//        @JoinColumn(name="languageId")
        private var language: Language? = null,

        var active: Boolean=false,

//        @OneToMany(fetch = FetchType.LAZY,mappedBy = "newspaper",targetEntity = Page::class)
        @Transient
        private var pageList: List<Page>? = null


): NsParserRestDbEntity {

        @Transient
        private var countryName:String?=null
        @Transient
        private var languageId:String?=null

        @Transient
        @XmlElement
        fun getCountryName():String? {
                return country?.name
        }

        @Transient
        @XmlElement
        fun getLanguageId(): String? {
                return language?.id
        }

        @JsonIgnore
        @XmlTransient
        fun getCountry(): Country? {
                return this.country
        }

        fun setCountry(country: Country?) {
                this.country = country
        }

        @JsonIgnore
        @XmlTransient
        fun getLanguage(): Language? {
                return this.language
        }

        fun setLanguage(language: Language?) {
                this.language = language
        }

        @JsonIgnore
        @XmlTransient
        fun getPageList(): List<Page>? {
                return this.pageList
        }

        fun setPageList(pageList: List<Page>?) {
                this.pageList = pageList
        }

        fun setCountryData(countries: List<Country>){
                countries.asSequence().forEach {
                        if (it.name == countryName){
                                country = it
                                return@forEach
                        }
                }
        }

        fun setLanguageData(languages: List<Language>){
                languages.asSequence().forEach {
                        if (it.id == languageId){
                                language = it
                                return@forEach
                        }
                }
        }

        override fun toString(): String {
                return "Newspaper(id='$id', name=$name, country=$country, language=$language, active=$active)"
        }
}