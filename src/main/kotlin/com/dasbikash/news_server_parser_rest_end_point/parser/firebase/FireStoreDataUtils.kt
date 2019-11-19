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

import com.dasbikash.news_server_parser_rest_end_point.model.database.PageDownLoadRequestResponse
import com.dasbikash.news_server_parser_rest_end_point.services.PageDownloadRequestEntryService
import com.dasbikash.news_server_parser_rest_end_point.utills.LoggerService
import com.google.cloud.firestore.DocumentChange
import com.google.cloud.firestore.FirestoreException
import com.google.cloud.firestore.QuerySnapshot


class FireStoreDataUtils private constructor(
        private val loggerService:LoggerService,
        private val pageDownloadRequestEntryService: PageDownloadRequestEntryService
) {
    init{
        FireStoreRefUtils.getPageDownloadRequestResponseCollectionRef()
                .addSnapshotListener(object : com.google.cloud.firestore.EventListener<QuerySnapshot> {
                    override fun onEvent(value: QuerySnapshot?, error: FirestoreException?) {
                        error?.let {
                            it.printStackTrace()
                            return
                        }
                        value?.let {
                            it.documentChanges.asSequence().forEach {
                                when(it.type){
                                    DocumentChange.Type.ADDED ->{
                                        val document = it.document
                                        val pageDownLoadRequestResponse =
                                                document.toObject(PageDownLoadRequestResponse::class.java)
                                        loggerService.logOnConsole("DocumentChange.Type.ADDED : ${document.id}")
                                        loggerService.logOnConsole(pageDownLoadRequestResponse.toString())
                                        pageDownloadRequestEntryService.findPageDownloadRequestEntryByResponseId(document.id)
                                            ?.let {
                                                if (it.requestKey.isBlank()){
                                                    throw IllegalStateException()
                                                }
                                                RealTimeDbDataUtils.deleteRequest(it.requestKey)
                                                FireStoreRefUtils.getPageDownloadRequestResponseCollectionRef()
                                                        .document(document.id)
                                                        .delete()
                                                it.setResponseContentFromServerResponse(pageDownLoadRequestResponse)
                                                pageDownloadRequestEntryService.save(it)
                                                loggerService.logOnConsole(it.toString())
                                        }
                                    }
                                    DocumentChange.Type.MODIFIED ->{
                                        loggerService.logOnConsole("DocumentChange.Type.MODIFIED : ${it.document.id}")
                                    }
                                    DocumentChange.Type.REMOVED ->{
                                        loggerService.logOnConsole("DocumentChange.Type.REMOVED : ${it.document.id}")
                                    }
                                }
                            }
                        }
                    }
                })
    }

    companion object {
        @Volatile
        private lateinit var INSTANCE: FireStoreDataUtils

        internal fun getInstance(loggerService:LoggerService,
                                 pageDownloadRequestEntryService: PageDownloadRequestEntryService): FireStoreDataUtils {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(FireStoreDataUtils::class.java) {
                    if (!Companion::INSTANCE.isInitialized) {
                        INSTANCE = FireStoreDataUtils(loggerService,pageDownloadRequestEntryService)
                    }
                }
            }
            return INSTANCE
        }
    }
}