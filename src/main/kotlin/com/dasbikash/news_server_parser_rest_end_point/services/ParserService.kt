package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.parser_related.handler.ParserExceptionHandlerService
import com.dasbikash.news_server_parser_rest_end_point.utils.ReportGenerationService
import com.dasbikash.news_server_parser_rest_end_point.Init.SettingsBootstrapService
import com.dasbikash.news_server_parser_rest_end_point.exceptions.parser_related.ReportGenerationException
import com.dasbikash.news_server_parser_rest_end_point.exceptions.parser_related.generic.HighestLevelException
import com.dasbikash.news_server_parser_rest_end_point.parser.parser_threads.ArticleDataFetcherForPageSelf
import com.dasbikash.news_server_parser_rest_end_point.parser.parser_threads.ArticleDataFetcherForPageThroughClient
import com.dasbikash.news_server_parser_rest_end_point.services.*
import com.dasbikash.news_server_parser_rest_end_point.utills.DateUtils
import com.dasbikash.news_server_parser_rest_end_point.utills.LoggerService
import com.dasbikash.news_server_parser_rest_end_point.utills.RxJavaService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import java.util.*

@Service
open class ParserService(
        private var settingsBootstrapService: SettingsBootstrapService?=null,
        private var newsPaperService: NewsPaperService?=null,
        private var reportGenerationService: ReportGenerationService?=null,
        private var parserExceptionHandlerService: ParserExceptionHandlerService?=null,
        private var pageService: PageService?=null,
        private var articleService: ArticleService?=null,
        private var loggerService: LoggerService?=null,
        private var rxJavaService: RxJavaService?=null,
        private var pageParsingIntervalService: PageParsingIntervalService?=null,
        private var pageParsingHistoryService: PageParsingHistoryService?=null,
        private var pageDownloadRequestEntryService: PageDownloadRequestEntryService?=null,
        private var authTokenService: AuthTokenService?=null,
        private var newspaperOpModeEntryService: NewspaperOpModeEntryService?=null
)
    :CommandLineRunner {

    private val ITERATION_DELAY = 15 * 60 * 1000L //15 mins

    private var articleDataFetcherForPageSelf: ArticleDataFetcherForPageSelf?=null
    private var articleDataFetcherForPageThroughClient: ArticleDataFetcherForPageThroughClient?=null

    private lateinit var currentDate: Calendar

    override fun run(vararg args: String?) {
        settingsBootstrapService!!.bootstrapSettingsIfRequired()

        currentDate = Calendar.getInstance()
        do {
            try {

                if (articleDataFetcherForPageSelf == null){
                    if ((newsPaperService!!.getNpCountWithRunningOpMode() +
                                    newsPaperService!!.getNpCountWithGetSyncedOpMode()) > 0){
                        articleDataFetcherForPageSelf =
                                ArticleDataFetcherForPageSelf(
                                        pageService!!,articleService!!,loggerService!!,rxJavaService!!,
                                        newsPaperService!!,pageParsingIntervalService!!,
                                        pageParsingHistoryService!!,parserExceptionHandlerService!!)
                        articleDataFetcherForPageSelf!!.start()
                    }
                }else{
                    if ((newsPaperService!!.getNpCountWithRunningOpMode()+
                                    newsPaperService!!.getNpCountWithGetSyncedOpMode()) == 0){
                        articleDataFetcherForPageSelf!!.interrupt()
                        articleDataFetcherForPageSelf = null
                    }else if (!articleDataFetcherForPageSelf!!.isAlive){
                        articleDataFetcherForPageSelf = ArticleDataFetcherForPageSelf(
                                pageService!!,articleService!!,loggerService!!,rxJavaService!!,
                                newsPaperService!!,pageParsingIntervalService!!,
                                pageParsingHistoryService!!,parserExceptionHandlerService!!)
                        articleDataFetcherForPageSelf!!.start()
                    }
                }

                if (articleDataFetcherForPageThroughClient == null){
                    if ((newsPaperService!!.getNpCountWithParseThroughClientOpMode()) > 0){
                        articleDataFetcherForPageThroughClient = ArticleDataFetcherForPageThroughClient(
                                pageService!!,articleService!!,loggerService!!,rxJavaService!!,
                                newsPaperService!!,pageParsingIntervalService!!,
                                pageParsingHistoryService!!,parserExceptionHandlerService!!,pageDownloadRequestEntryService!!)
                        articleDataFetcherForPageThroughClient!!.start()
                    }
                }else{
                    if ((newsPaperService!!.getNpCountWithParseThroughClientOpMode()) == 0){
                        articleDataFetcherForPageThroughClient!!.interrupt()
                        articleDataFetcherForPageThroughClient = null
                    }else if (!articleDataFetcherForPageThroughClient!!.isAlive){
                        articleDataFetcherForPageThroughClient = ArticleDataFetcherForPageThroughClient(
                                pageService!!,articleService!!,loggerService!!,rxJavaService!!,
                                newsPaperService!!,pageParsingIntervalService!!,
                                pageParsingHistoryService!!,parserExceptionHandlerService!!,pageDownloadRequestEntryService!!)
                        articleDataFetcherForPageThroughClient!!.start()
                    }
                }

                val now = Calendar.getInstance()
                if (now.get(Calendar.YEAR)> currentDate.get(Calendar.YEAR) ||
                        now.get(Calendar.DAY_OF_YEAR)> currentDate.get(Calendar.DAY_OF_YEAR)){
                    try {

                        generateAndDistributeDailyReport(now.time!!)

                        if (DateUtils.isFirstDayOfWeek(now.time)) {
                            generateAndDistributeWeeklyReport(now.time)
                        }

                        if (DateUtils.isFirstDayOfMonth(now.time)) {
                            generateAndDistributeMonthlyReport(now.time)
                        }

                        currentDate = now
                    }catch (ex:Throwable){
                        ex.printStackTrace()
                        parserExceptionHandlerService!!.handleException(ReportGenerationException(ex))
                    }
                }

//                RealTimeDbAdminTaskUtils.getInstance(loggerService!!, rxJavaService!!, authTokenService!!, newsPaperService!!,
//                        newspaperOpModeEntryService!!, pageParsingHistoryService!!, pageService!!).init()
                Thread.sleep(ITERATION_DELAY)
            } catch (ex: InterruptedException) {
                ex.printStackTrace()
                handleException(ex)
            }
        } while (true)
    }

    private fun generateAndDistributeDailyReport(today: Date) {
        println("Starting daily article parsing report generation.")
        reportGenerationService!!.prepareDailyReport(today)
        println("Daily article parsing report generated.")
        reportGenerationService!!.emailDailyReport(today)
        println("Daily article parsing report distributed.")
    }

    private fun generateAndDistributeWeeklyReport(today: Date) {
        println("Starting weekly article parsing report generation.")
        reportGenerationService!!.prepareWeeklyReport(today)
        println("Weekly article parsing report generated.")
        reportGenerationService!!.emailWeeklyReport(today)
        println("Weekly article parsing report distributed.")
    }

    private fun generateAndDistributeMonthlyReport(today: Date) {
        println("Starting monthly article parsing report generation.")
        reportGenerationService!!.prepareMonthlyReport(today)
        println("Monthly article parsing report generated.")
        reportGenerationService!!.emailMonthlyReport(today)
        println("Monthly article parsing report distributed.")
    }

    private fun handleException(ex: InterruptedException) {
        parserExceptionHandlerService!!.handleException(HighestLevelException(ex))
    }
}