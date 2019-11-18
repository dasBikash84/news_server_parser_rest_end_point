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

package com.dasbikash.news_server_parser_rest_end_point.parser.article_body_parsers.dainik_amader_shomoy

import com.dasbikash.news_server_parser_rest_end_point.parser.ArticleBodyParser
import org.jsoup.select.Elements

class AmaderSomoyArticleParser : ArticleBodyParser() {

    private val mSiteBaseAddress = "http://www.dainikamadershomoy.com"

    override fun getSiteBaseAddress(): String {
        return mSiteBaseAddress
    }

    override fun getArticleModificationDateString(): String? {
        return null
    }

    override fun getArticleModificationDateStringFormats(): Array<String>? {
        return null
    }

    override fun getFeaturedImageSelector(): String {
        return AmaderSomoyArticleParserInfo.FEATURED_IMAGE_SELECTOR
    }

    override fun getFeaturedImageLinkSelectorAttr(): String {
        return AmaderSomoyArticleParserInfo.FEATURED_IMAGE_LINK_SELECTOR_ATTR
    }

    override fun getFeaturedImageCaptionSelector(): String {
        return AmaderSomoyArticleParserInfo.FEATURED_IMAGE_CAPTION_SELECTOR
    }

    override fun getFeaturedImageCaptionSelectorAttr(): String? {
        return null
    }

    override fun getArticleFragmentBlocks(): Elements {
        return mDocument.select(
                AmaderSomoyArticleParserInfo.ARTICLE_FRAGMENT_BLOCK_SELECTOR
        )
    }

    override fun getParagraphImageSelector(): String {
        return AmaderSomoyArticleParserInfo.PARAGRAPH_IMAGE_SELECTOR
    }

    override fun getParagraphImageLinkSelectorAttr(): String {
        return AmaderSomoyArticleParserInfo.PARAGRAPH_IMAGE_LINK_SELECTOR_ATTR
    }

    override fun getParagraphImageCaptionSelector(): String? {
        return null
    }

    override fun getParagraphImageCaptionSelectorAttr(): String {
        return AmaderSomoyArticleParserInfo.PARAGRAPH_IMAGE_CAPTION_SELECTOR_ATTR
    }
}
