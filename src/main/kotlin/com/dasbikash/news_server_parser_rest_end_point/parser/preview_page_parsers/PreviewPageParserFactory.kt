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

package com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers

import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.bangla_news_24_bn.BanglaNews24BnPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.bangla_news_24_en.BanglaNews24EnPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.bd_pratidin.BdPratidinPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.bhorer_kagoj.BhorerKagojPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.bonik_barta.BonikBartaPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.daily_mirror.DailyMirrorPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.daily_observer.DailyObserverPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.daily_sun.DailySunPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.dainik_amader_shomoy.AmaderSomoyPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.dawn.DawnPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.dhaka_tribune.DhakaTribunePreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.doinick_ittefaq.DoinickIttefaqPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.jaijaidin.JaiJaiDinPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.jugantor.JugantorPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.kaler_kantho.KalerKanthoPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.manob_zamin.ManobZaminPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.new_age.NewAgePreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.prothom_alo.ProthomaloPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.the_daily_star.TheDailyStarPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.the_financial_express.TheFinancialExpressPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.the_gurdian.TheGurdianPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.the_indian_express.TheIndianExpressPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.the_times_of_india.TheTimesOfIndiaPreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.model.database.Newspaper
import com.dasbikash.news_server_parser_rest_end_point.parser.NEWS_PAPER_ID
import com.dasbikash.news_server_parser_rest_end_point.parser.PreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.anando_bazar.AnandoBazarPreviewPageParser

internal object PreviewPageParserFactory {

    fun getPreviewPageParserByNewsPaper(newspaper: Newspaper): PreviewPageParser? {

        return when(newspaper.id){
            NEWS_PAPER_ID.THE_GUARDIAN.id-> TheGurdianPreviewPageParser()
            NEWS_PAPER_ID.PROTHOM_ALO.id-> ProthomaloPreviewPageParser()
            NEWS_PAPER_ID.ANANDO_BAZAR.id-> AnandoBazarPreviewPageParser()
            NEWS_PAPER_ID.THE_DAILY_STAR.id-> TheDailyStarPreviewPageParser()
            NEWS_PAPER_ID.THE_INDIAN_EXPRESS.id-> TheIndianExpressPreviewPageParser()
            NEWS_PAPER_ID.DOINIK_ITTEFAQ.id-> DoinickIttefaqPreviewPageParser()
            NEWS_PAPER_ID.THE_TIMES_OF_INDIA.id-> TheTimesOfIndiaPreviewPageParser()
            NEWS_PAPER_ID.DAILY_MIRROR.id-> DailyMirrorPreviewPageParser()
            NEWS_PAPER_ID.DHAKA_TRIBUNE.id-> DhakaTribunePreviewPageParser()
            NEWS_PAPER_ID.BANGLADESH_PROTIDIN.id-> BdPratidinPreviewPageParser()
            NEWS_PAPER_ID.DAWN.id-> DawnPreviewPageParser()
            NEWS_PAPER_ID.KALER_KONTHO.id-> KalerKanthoPreviewPageParser()
            NEWS_PAPER_ID.JUGANTOR.id-> JugantorPreviewPageParser()
            NEWS_PAPER_ID.THE_FINANCIAL_EXPRESS.id-> TheFinancialExpressPreviewPageParser()
            NEWS_PAPER_ID.BONIK_BARTA.id-> BonikBartaPreviewPageParser()
            NEWS_PAPER_ID.BHORER_KAGOJ.id-> BhorerKagojPreviewPageParser()
            NEWS_PAPER_ID.NEW_AGE.id-> NewAgePreviewPageParser()
            NEWS_PAPER_ID.DAILY_SUN.id-> DailySunPreviewPageParser()
            NEWS_PAPER_ID.JAI_JAI_DIN.id-> JaiJaiDinPreviewPageParser()
            NEWS_PAPER_ID.AMADER_SOMOY.id-> AmaderSomoyPreviewPageParser()
            NEWS_PAPER_ID.DAILY_OBSERVER.id-> DailyObserverPreviewPageParser()
            NEWS_PAPER_ID.BANGLA_NEWS_24_BN.id-> BanglaNews24BnPreviewPageParser()
            NEWS_PAPER_ID.BANGLA_NEWS_24_EN.id-> BanglaNews24EnPreviewPageParser()
            NEWS_PAPER_ID.MANOB_ZAMIN.id-> ManobZaminPreviewPageParser()
            else-> null
        }
    }
}