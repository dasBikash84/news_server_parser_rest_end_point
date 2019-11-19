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
import com.dasbikash.news_server_parser_rest_end_point.repositories.ErrorLogRepository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.springframework.stereotype.Service

@Service
open class RxJavaUtils(
        private var errorLogRepository: ErrorLogRepository?=null
) {

    fun doTaskInBackGround(task:()->Unit){
        Observable.just(true)
                .subscribeOn(Schedulers.io())
                .map { task() }
                .subscribeWith(object : Observer<Unit>{
                    override fun onComplete() {}

                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(t: Unit) {}

                    override fun onError(e: Throwable) {
//                        val session = DbSessionManager.getNewSession()
//                        LoggerUtils.logError(e,session)
//                        session.close()
                        errorLogRepository!!.save(ErrorLog(e))
                        e.printStackTrace()
                    }
                })
    }
}