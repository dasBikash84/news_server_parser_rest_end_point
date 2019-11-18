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

package com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.dainik_amader_shomoy

import com.dasbikash.news_server_parser_rest_end_point.parser.PreviewPageParser
import com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.dainik_amader_shomoy.AmaderSomoyPreviewPageParserInfo
import com.dasbikash.news_server_parser_rest_end_point.utills.DisplayUtils
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


class AmaderSomoyPreviewPageParser : PreviewPageParser() {
    private val mSiteBaseAddress = "http://www.dainikamadershomoy.com"

    override fun calculatePageLink(): String {
        mCurrentPageNumber = (mCurrentPageNumber-1)*20
        return super.calculatePageLink()
    }

    override fun getSiteBaseAddress(): String {
        return mSiteBaseAddress
    }

    override fun getArticlePublicationDatetimeFormat(): String {
        return AmaderSomoyPreviewPageParserInfo.ARTICLE_PUBLICATION_DATE_TIME_FORMAT
    }

    override fun getPreviewBlocks(): Elements {
        return mDocument.select(AmaderSomoyPreviewPageParserInfo.ARTICLE_PREVIEW_BLOCK_SELECTOR)
    }

    override fun getArticleLink(previewBlock: Element): String {
        return previewBlock.select(AmaderSomoyPreviewPageParserInfo.ARTICLE_LINK_ELEMENT_SELECTOR)[0].attr(AmaderSomoyPreviewPageParserInfo.ARTICLE_LINK_TEXT_SELECTOR_TAG)
    }

    override fun getArticlePreviewImageLink(previewBlock: Element): String {
        return previewBlock.select(AmaderSomoyPreviewPageParserInfo.ARTICLE_PREVIEW_IMAGE_LINK_ELEMENT_SELECTOR)[0]
                                            .attr(AmaderSomoyPreviewPageParserInfo.ARTICLE_PREVIEW_IMAGE_LINK_TEXT_SELECTOR_TAG)
    }

    override fun getArticleTitle(previewBlock: Element): String {
        return previewBlock.select(AmaderSomoyPreviewPageParserInfo.ARTICLE_TITLE_ELEMENT_SELECTOR)[0].text()
    }

    override fun getArticlePublicationDateString(previewBlock: Element): String {
        val articlePublicationDateString = previewBlock.select(AmaderSomoyPreviewPageParserInfo.ARTICLE_PUBLICATION_DATE_ELEMENT_SELECTOR)[0].text()
        return DisplayUtils.banglaToEnglishDateString(articlePublicationDateString)
    }

    companion object {
        private val TAG = "AmaderSomoy"
    }
}