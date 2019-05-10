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

object DatabaseTableNames {
    const val COUNTRY_TABLE_NAME = "countries"
    const val LANGUAGE_TABLE_NAME = "languages"
    const val NEWSPAPER_TABLE_NAME = "newspapers"
    const val PAGE_TABLE_NAME = "pages"
    const val ARTICLE_TABLE_NAME = "articles"
    const val PAGE_GROUP_TABLE_NAME = "page_groups"
    const val AUTH_TOKEN_TABLE_NAME = "tokens"
    const val GENERAL_LOG_TABLE_NAME = "general_log"
    const val ERROR_LOG_TABLE_NAME = "exception_log"
    const val PAGE_PARSING_HISTORY_TABLE_NAME = "page_parsing_history"
}