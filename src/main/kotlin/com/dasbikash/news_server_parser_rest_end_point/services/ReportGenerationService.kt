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

package com.dasbikash.news_server_parser_rest_end_point.utils

import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.services.ArticleService
import com.dasbikash.news_server_parser_rest_end_point.services.PageParsingHistoryService
import com.dasbikash.news_server_parser_rest_end_point.services.PageService
import com.dasbikash.news_server_parser_rest_end_point.utills.DateUtils
import com.dasbikash.news_server_parser_rest_end_point.utills.EmailUtils
import com.dasbikash.news_server_parser_rest_end_point.utills.FileUtils
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

@Service
open class ReportGenerationService(
        private var pageService: PageService?=null,
        private var articleService: ArticleService?=null,
        private var pageParsingHistoryService: PageParsingHistoryService?=null
) {

    fun getTableHeader(): String {
        return "Sl,Page Name,Weekly,Page Id,Parent Page Id,Newspaper,Article Download Count"
    }

    fun prepareDailyReport(today: Date) {

        val reportFilePath = FileUtils.getDailyReportFilePath(today)
        if (File(reportFilePath).exists()) {
            File(reportFilePath).delete()
        }
        val reportFile = File(reportFilePath)
        val yesterDay = DateUtils.getYesterDay(today)

        reportFile.appendText("Article parsing report of: ${DateUtils.getDateStringForDb(yesterDay)}\n\n")

        val pages = pageService!!.getAllPages()
        val pageArticleCountMap = mutableMapOf<Page,Int>()
        pages.asSequence().filter { it.isHasData() }.sortedBy { it.getNewspaper()!!.name!! }.forEach {
            val articleCountOfYesterday = getArticleCountForPageOfYesterday(it, today)
            pageArticleCountMap.put(it,articleCountOfYesterday)
        }
        addReportDataToFile(reportFile, pageArticleCountMap)
//        addFromBeginningReport(reportFile, pages, session)
    }

    fun prepareWeeklyReport(today: Date) {

        val reportFilePath = FileUtils.getWeeklyReportFilePath(today)

        if (File(reportFilePath).exists()) {
            File(reportFilePath).delete()
        }
        val reportFile = File(reportFilePath)

        val lastWeekFirstDay = DateUtils.getLastWeekSameDay(today)
        val lastWeekLastDay = DateUtils.getYesterDay(today)

        reportFile.appendText("Article parsing report of week: ${DateUtils.getDateStringForDb(lastWeekFirstDay)} to "+
                                "${DateUtils.getDateStringForDb(lastWeekLastDay)}\n\n")

        val pages = pageService!!.getAllPages()
        val pageArticleCountMap = mutableMapOf<Page,Int>()
        pages.asSequence().filter { it.isHasData() }.sortedBy { it.getNewspaper()!!.name!! }.forEach {
            val articleCountOfLastWeek = getArticleCountForPageOfLastWeek(it, today)
            pageArticleCountMap.put(it,articleCountOfLastWeek)
        }

        addReportDataToFile(reportFile, pageArticleCountMap)
//        addFromBeginningReport(reportFile, pages, session)
    }

    fun prepareMonthlyReport(today: Date) {

        val reportFilePath = FileUtils.getMonthlyReportFilePath(today)

        if (File(reportFilePath).exists()) {
            File(reportFilePath).delete()
        }
        val reportFile = File(reportFilePath)
        val firstDayOfLastMonth = DateUtils.getFirstDayOfLastMonth(today)

        reportFile.appendText("Article parsing report of: ${DateUtils.getYearMonthStr(firstDayOfLastMonth)}\n\n")

        val pages = pageService!!.getAllPages()
        val pageArticleCountMap = mutableMapOf<Page,Int>()

        pages.asSequence().filter { it.isHasData() }.sortedBy { it.getNewspaper()!!.name!! }.forEach {
            val articleCountOfLastMonth = getArticleCountForPageOfLastMonth(it, today)
            pageArticleCountMap.put(it,articleCountOfLastMonth)
        }

        addReportDataToFile(reportFile, pageArticleCountMap)
        addFromBeginningReport(reportFile, pages)
    }

    private fun addReportDataToFile(reportFile: File, pageArticleCountMap: MutableMap<Page, Int>) {
        reportFile.appendText("For pages with articles:\n")
        reportFile.appendText("${getTableHeader()}\n")

        var sln = 0
        var totalArticleCountOfLastMonth = 0

        pageArticleCountMap.keys.asSequence().filter { pageArticleCountMap.get(it) != 0 }.forEach {
            val articleCountOfLastMonth = pageArticleCountMap.get(it)!!
            totalArticleCountOfLastMonth += articleCountOfLastMonth
            reportFile.appendText("${++sln},${it.name},${getIsWeeklyText(it)},${it.id},${getParentPageIdText(it)},${it.getNewspaper()!!.name},${articleCountOfLastMonth}\n")
        }
        reportFile.appendText(",,,,,Total,${totalArticleCountOfLastMonth}\n\n")

        reportFile.appendText("For pages without any article:\n")
        reportFile.appendText("${getTableHeader()}\n")
        sln = 0

        pageArticleCountMap.keys.asSequence().filter { pageArticleCountMap.get(it) == 0 }.forEach {
            val articleCountOfLastMonth = pageArticleCountMap.get(it)!!
            reportFile.appendText("${++sln},${it.name},${getIsWeeklyText(it)},${it.id},${getParentPageIdText(it)},${it.getNewspaper()!!.name},${articleCountOfLastMonth}\n")
        }
    }

    fun emailDailyReport(today: Date) {
        val reportFilePath = FileUtils.getDailyReportFilePath(today)
        val yesterDay = DateUtils.getYesterDay(today)

        EmailUtils.sendEmail("Daily parsing report",
                                "Parsing report of ${DateUtils.getDateStringForDb(yesterDay)}",
                                        reportFilePath)
    }

    fun emailWeeklyReport(today: Date) {
        val reportFilePath = FileUtils.getWeeklyReportFilePath(today)
        val lastWeekFirstDay = DateUtils.getLastWeekSameDay(today)
        val lastWeekLastDay = DateUtils.getYesterDay(today)

        EmailUtils.sendEmail("Weekly parsing report",
                                "Parsing report of ${DateUtils.getDateStringForDb(lastWeekFirstDay)} to ${DateUtils.getDateStringForDb(lastWeekLastDay)}",
                                        reportFilePath)
    }

    fun emailMonthlyReport(today: Date) {
        val reportFilePath = FileUtils.getMonthlyReportFilePath(today)
        val firstDayOfLastMonth = DateUtils.getFirstDayOfLastMonth(today)

        EmailUtils.sendEmail("Monthly parsing report",
                                "Parsing report of ${DateUtils.getYearMonthStr(firstDayOfLastMonth)}",
                                    reportFilePath)
    }

    private fun addFromBeginningReport(reportFile: File, pages: List<Page>) {
        reportFile.appendText("\n\nParsing report from Beginning:\n\n")

        val pageArticleCountMap = mutableMapOf<Page,Int>()
        pages.asSequence().filter { it.isHasData() }.sortedBy { it.getNewspaper()!!.name!! }.forEach {
            val articleCountFromBeginning = articleService!!.getArticleCountForPage(it)
            pageArticleCountMap.put(it,articleCountFromBeginning)
        }
        addReportDataToFile(reportFile, pageArticleCountMap)
    }

    private fun getParentPageIdText(page:Page):String{
        if (page.isTopLevelPage()){
            return "-"
        }
        return page.parentPageId!!
    }

    private fun getIsWeeklyText(page:Page):String{
        if (page.weekly){
            return "Y"
        }
        return "-"
    }

    private fun getArticleCountForPageOfYesterday(page: Page, today: Date): Int {
        val yesterday = DateUtils.getYesterDay(today)
        return getArticleCountForPageBetweenTwoDates(page, yesterday, today)
    }

    private fun getArticleCountForPageOfLastWeek(page: Page, thisWeekFirstDay: Date): Int {
        val lastWeekFirstDay = DateUtils.getLastWeekSameDay(thisWeekFirstDay)
        return getArticleCountForPageBetweenTwoDates(page, lastWeekFirstDay, thisWeekFirstDay)
    }

    private fun getArticleCountForPageOfLastMonth(page: Page, anyDayOfMonth: Date): Int {
        val firstDayOfMonth = DateUtils.getFirstDayOfMonth(anyDayOfMonth)
        val firstDayOfLastMonth = DateUtils.getFirstDayOfLastMonth(anyDayOfMonth)

        return getArticleCountForPageBetweenTwoDates(page, firstDayOfLastMonth, firstDayOfMonth)
    }

    private fun getArticleCountForPageBetweenTwoDates(page: Page, startDate: Date, endDate: Date): Int{
        return pageParsingHistoryService!!.getArticleCountForPageBetweenTwoDates(
                page,DateUtils.getDateStringForDb(startDate),DateUtils.getDateStringForDb(endDate))
    }
}