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

package com.dasbikash.news_server_parser_rest_end_point.parser.preview_page_parsers.dhaka_tribune

import com.dasbikash.news_server_parser_rest_end_point.parser.PreviewPageParser
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


class DhakaTribunePreviewPageParser : PreviewPageParser() {

    private val mSiteBaseAddress = "https://www.dhakatribune.com"

    /*@Override
    protected int getMaxReRunCountOnEmptyWithRepeat() {
        return MAX_RERUN_COUNT_FOR_EMPTY_WITH_REPEAT_FOR_REGULAR_FEATURE;
    }*/

    override fun getSiteBaseAddress(): String {
        return mSiteBaseAddress
    }

    override fun getArticlePublicationDatetimeFormat(): String {
        return DhakaTribunePreviewPageParserInfo.ARTICLE_PUBLICATION_DATE_TIME_FORMAT
    }

    override fun getPreviewBlocks(): Elements {
        return mDocument.select(DhakaTribunePreviewPageParserInfo.ARTICLE_PREVIEW_BLOCK_SELECTOR)
    }

    override fun getArticleLink(previewBlock: Element): String {
        return previewBlock.select(DhakaTribunePreviewPageParserInfo.ARTICLE_LINK_ELEMENT_SELECTOR)[0].attr(DhakaTribunePreviewPageParserInfo.ARTICLE_LINK_TEXT_SELECTOR_TAG)
    }

    override fun getArticlePreviewImageLink(previewBlock: Element): String {
        return previewBlock.select(DhakaTribunePreviewPageParserInfo.ARTICLE_PREVIEW_IMAGE_LINK_ELEMENT_SELECTOR)[0].attr(DhakaTribunePreviewPageParserInfo.ARTICLE_PREVIEW_IMAGE_LINK_TEXT_SELECTOR_TAG)
    }

    override fun getArticleTitle(previewBlock: Element): String {
        return previewBlock.select(DhakaTribunePreviewPageParserInfo.ARTICLE_TITLE_ELEMENT_SELECTOR)[0].text()
    }

    override fun getArticlePublicationDateString(previewBlock: Element): String {
        return previewBlock.select(DhakaTribunePreviewPageParserInfo.ARTICLE_PUBLICATION_DATE_ELEMENT_SELECTOR)[0].text().trim { it <= ' ' }
    }

    companion object {

        //private static final String TAG = "StackTrace";
        private val TAG = "DhkTEdLoader"

        private val MAX_RERUN_COUNT_FOR_EMPTY_WITH_REPEAT_FOR_REGULAR_FEATURE = 6
    }
}