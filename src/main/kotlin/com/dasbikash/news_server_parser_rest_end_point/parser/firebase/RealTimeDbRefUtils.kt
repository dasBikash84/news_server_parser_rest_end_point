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

package com.dasbikash.news_server_parser_rest_end_point.parser.firebase

import com.google.firebase.database.DatabaseReference

internal object RealTimeDbRefUtils {

    private const val PAGE_DOWNLOAD_REQUEST_NODE = "page_download_request"
    private const val ADMIN_TASK_DATA_NODE = "admin_task_data"

    private val mRootReference:DatabaseReference

    init{
        mRootReference = FireBaseConUtils.mFirebaseDatabaseCon.reference
    }

    fun getPageDownloadRequestRef():DatabaseReference = mRootReference.child(PAGE_DOWNLOAD_REQUEST_NODE)
    fun getAdminTaskDataNode():DatabaseReference = mRootReference.child(ADMIN_TASK_DATA_NODE)

}