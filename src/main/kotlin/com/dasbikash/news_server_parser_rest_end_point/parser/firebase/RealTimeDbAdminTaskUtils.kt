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

import com.dasbikash.news_server_parser_rest_end_point.model.NewsPaperParserModeChangeRequest
import com.dasbikash.news_server_parser_rest_end_point.model.database.NewspaperOpModeEntry
import com.dasbikash.news_server_parser_rest_end_point.model.database.PageParsingHistory
import com.dasbikash.news_server_parser_rest_end_point.model.database.ParserMode
import com.dasbikash.news_server_parser_rest_end_point.model.database.TokenGenerationRequest
import com.dasbikash.news_server_parser_rest_end_point.services.*
import com.dasbikash.news_server_parser_rest_end_point.utills.EmailUtils
import com.dasbikash.news_server_parser_rest_end_point.utills.LoggerService
import com.dasbikash.news_server_parser_rest_end_point.utills.RxJavaUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class RealTimeDbAdminTaskUtils private constructor(
        val loggerService: LoggerService,
        val rxJavaUtils: RxJavaUtils,
        val authTokenService: AuthTokenService,
        val newsPaperService: NewsPaperService,
        val newspaperOpModeEntryService: NewspaperOpModeEntryService,
        val pageParsingHistoryService: PageParsingHistoryService,
        val pageService: PageService
){
    init {
        RealTimeDbRefUtils.getAdminTaskDataNode()
                .child(TOKEN_GENERATION_REQUEST_NODE)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) {}

                    override fun onDataChange(snapshot: DataSnapshot?) {
                        rxJavaUtils.doTaskInBackGround {
                            snapshot?.let {
                                it.children.asSequence().forEach {
                                    val tokenGenerationRequest = it.getValue(TokenGenerationRequest::class.java)
                                    if (tokenGenerationRequest.isValid()) {
                                        val token = authTokenService.getNewAuthToken()
                                        EmailUtils.emailAuthTokenToAdmin(token,this@RealTimeDbAdminTaskUtils::class.java)
                                        loggerService.logOnDb("New auth token generated.")
                                    }
                                }
                                deleteTokenGenerationRequest()
                            }
                        }
                    }
                })

        RealTimeDbRefUtils.getAdminTaskDataNode()
                .child(NP_PARSER_MODE_CHANGE_REQUEST_NODE)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) {}

                    override fun onDataChange(snapshot: DataSnapshot?) {
                        rxJavaUtils.doTaskInBackGround {
                            snapshot?.let {

                                it.children.asSequence().forEach {
                                    val newsPaperParserModeChangeRequest =
                                            it.getValue(NewsPaperParserModeChangeRequest::class.java)

                                    val token = authTokenService.findToken(newsPaperParserModeChangeRequest.authToken)

                                    val newspaper = newsPaperService.findById(newsPaperParserModeChangeRequest.targetNewspaperId)

                                    if (newsPaperParserModeChangeRequest.hasValidMode() && (newspaper != null) &&
                                            token != null && !token.hasExpired()) {
                                        val oldMode = newsPaperService.getLatestOpModeEntryForNewspaper(newspaper)
                                        var npOpModeEntry:NewspaperOpModeEntry?=null
                                        if (newsPaperParserModeChangeRequest.isRunningRequest()) {
                                            npOpModeEntry = NewspaperOpModeEntry(opMode = ParserMode.RUNNING, newspaper = newspaper)
                                        } else if (newsPaperParserModeChangeRequest.isGetSyncedRequest()) {
                                            npOpModeEntry = NewspaperOpModeEntry(opMode = ParserMode.GET_SYNCED, newspaper = newspaper)
                                        } else if (newsPaperParserModeChangeRequest.isParseThroughClientRequest()) {
                                            npOpModeEntry = NewspaperOpModeEntry(opMode = ParserMode.PARSE_THROUGH_CLIENT, newspaper = newspaper)
                                        } else if (newsPaperParserModeChangeRequest.isOffRequest()) {
                                            npOpModeEntry = NewspaperOpModeEntry(opMode = ParserMode.OFF, newspaper = newspaper)
                                        }
                                        npOpModeEntry?.let {
                                            newspaperOpModeEntryService.save(it)
                                            loggerService.logOnDb("Operation mode set to ${it.getOpMode().name} for Np ${newspaper.name}.")
                                            if (oldMode.getOpMode()!=ParserMode.OFF &&
                                                    oldMode.getOpMode()!= it.getOpMode()){
                                                pageService.getAllPagesByNewspaperId(newspaper.id)
                                                        .filter { it.isHasData() && it.isPaginated() }.asSequence().forEach {
                                                            pageParsingHistoryService.save(PageParsingHistory.getEmptyParsingHistoryForPage(it))
                                                        }
                                            }
                                        }
                                        token.makeExpired()
                                        authTokenService.save(token)
                                    }
                                }
                                deleteNewsPaperParserModeChangeRequest()
                            }
                        }
                    }
                })
    }

    fun init() {}

    private fun deleteTokenGenerationRequest() {
        deleteParserAdminTaskDataForNode(TOKEN_GENERATION_REQUEST_NODE)
    }

    private fun deleteNewsPaperStatusChangeRequest() {
        deleteParserAdminTaskDataForNode(NP_STATUS_CHANGE_REQUEST_NODE)
    }

    private fun deleteNewsPaperParserModeChangeRequest() {
        deleteParserAdminTaskDataForNode(NP_PARSER_MODE_CHANGE_REQUEST_NODE)
    }

    private fun deleteParserAdminTaskDataForNode(nodeName: String) {
        RealTimeDbDataUtils.clearData(RealTimeDbRefUtils.getAdminTaskDataNode().child(nodeName))
    }

    companion object {

        private const val TOKEN_GENERATION_REQUEST_NODE = "parser_token_generation_request"
        private const val NP_STATUS_CHANGE_REQUEST_NODE = "np_status_change_request"
        private const val NP_PARSER_MODE_CHANGE_REQUEST_NODE = "np_parser_mode_change_request"

        @Volatile
        private lateinit var INSTANCE: RealTimeDbAdminTaskUtils

        internal fun getInstance(loggerService: LoggerService,rxJavaUtils: RxJavaUtils,authTokenService: AuthTokenService,
                                 newsPaperService: NewsPaperService,newspaperOpModeEntryService: NewspaperOpModeEntryService,
                                 pageParsingHistoryService: PageParsingHistoryService,pageService: PageService)
                : RealTimeDbAdminTaskUtils {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(RealTimeDbAdminTaskUtils::class.java) {
                    if (!Companion::INSTANCE.isInitialized) {
                        INSTANCE = RealTimeDbAdminTaskUtils(loggerService, rxJavaUtils, authTokenService, newsPaperService,
                                                                newspaperOpModeEntryService, pageParsingHistoryService, pageService)
                    }
                }
            }
            return INSTANCE
        }
    }
}