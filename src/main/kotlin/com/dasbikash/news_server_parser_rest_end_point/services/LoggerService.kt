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

package com.dasbikash.news_server_parser_rest_end_point.utills

import com.dasbikash.news_server_parser_rest_end_point.model.database.ErrorLog
import com.dasbikash.news_server_parser_rest_end_point.model.database.GeneralLog
import com.dasbikash.news_server_parser_rest_end_point.repositories.ErrorLogRepository
import com.dasbikash.news_server_parser_rest_end_point.repositories.GeneralLogRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
open class LoggerService(
        private var errorLogRepository: ErrorLogRepository?=null,
        private var generalLogRepository: GeneralLogRepository?=null
) {

    fun logOnDb(message: String) {
        generalLogRepository!!.save(GeneralLog(logMessage = message))
        logOnConsole(message)
    }

    fun logOnConsole(message: String) {
        println("${Date()}: ${message}")
    }

    fun logError( exception: Throwable){
        errorLogRepository!!.save(ErrorLog(exception))
    }
}
