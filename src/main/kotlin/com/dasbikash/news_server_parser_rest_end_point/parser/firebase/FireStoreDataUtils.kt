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

/*import com.dasbikash.news_server_parser.database.DatabaseUtils
import com.dasbikash.news_server_parser.database.DbSessionManager
import com.dasbikash.news_server_parser_rest_end_point.model.database.PageDownLoadRequestResponse
import com.dasbikash.news_server_parser.utils.LoggerUtils
import com.google.cloud.firestore.DocumentChange
import com.google.cloud.firestore.FirestoreException
import com.google.cloud.firestore.QuerySnapshot*/


object FireStoreDataUtils {

    fun nop(){}

    init {
        /*FireStoreRefUtils.getPageDownloadRequestResponseCollectionRef()
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
                                        LoggerUtils.logOnConsole("DocumentChange.Type.ADDED : ${document.id}")
                                        LoggerUtils.logOnConsole(pageDownLoadRequestResponse.toString())
                                        val session = DbSessionManager.getNewSession()
                                        val pageDownloadRequestEntry =
                                                DatabaseUtils.findPageDownloadRequestEntryBYServerNodeName(session,document.id)
                                        pageDownloadRequestEntry?.let {
                                            if (it.requestKey.isBlank()){
                                                throw IllegalStateException()
                                            }
                                            RealTimeDbDataUtils.deleteRequest(it.requestKey)
                                            FireStoreRefUtils.getPageDownloadRequestResponseCollectionRef()
                                                    .document(document.id)
                                                    .delete()
                                            it.setResponseContentFromServerResponse(pageDownLoadRequestResponse)
                                            DatabaseUtils.runDbTransection(session){
                                                session.update(it)
                                            }
                                            LoggerUtils.logOnConsole(it.toString())
                                        }
                                    }
                                    DocumentChange.Type.MODIFIED ->{
                                        LoggerUtils.logOnConsole("DocumentChange.Type.MODIFIED : ${it.document.id}")
                                    }
                                    DocumentChange.Type.REMOVED ->{
                                        LoggerUtils.logOnConsole("DocumentChange.Type.REMOVED : ${it.document.id}")
                                    }
                                }
                            }
                        }
                    }
                })*/

    }
}