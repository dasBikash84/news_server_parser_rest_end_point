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

import org.springframework.data.annotation.*
import java.util.*
//import javax.persistence.*
import javax.xml.bind.annotation.XmlRootElement

//@Entity
//@Table(name = DatabaseTableNames.ERROR_LOG_TABLE_NAME)
@XmlRootElement
class ErrorLog(exception: Throwable?=null): NsParserRestDbEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?=null
    var exceptionClassFullName: String? = null
//    @Enumerated(value = EnumType.STRING)
    var exceptionClassSimpleName: ExceptionClassNames? = null
//    @Column(columnDefinition = "text")
    var exceptionCause: String? = null
//    @Column(columnDefinition = "text")
    var exceptionMessage: String? = null
//    @Column(columnDefinition = "text")
    var stackTrace: String? = null
    var created: Date? = null

    init {
        exception?.let {
//            this.exception = it
            this.exceptionClassSimpleName = ExceptionClassNames.valueOf(it::class.java.simpleName)
            this.exceptionClassFullName = it::class.java.canonicalName
            this.exceptionCause = it.cause?.message ?: ""
            this.exceptionMessage = it.message ?: ""
            val stackTraceBuilder = StringBuilder("")
            var exp: Throwable = it
            do {
                exp.stackTrace.asSequence().forEach { stackTraceBuilder.append(it.toString()).append("\n") }
                if (exp.cause == null) {
                    break
                }
                exp = exp.cause!!
            } while (true)
            this.stackTrace = stackTraceBuilder.toString()
        }
    }
}