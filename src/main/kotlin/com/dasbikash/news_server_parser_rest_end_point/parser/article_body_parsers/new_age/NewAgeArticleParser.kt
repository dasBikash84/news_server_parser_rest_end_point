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

package com.dasbikash.news_server_parser_rest_end_point.parser.article_body_parsers.new_age

import com.dasbikash.news_server_parser_rest_end_point.parser.ArticleBodyParser
import org.jsoup.select.Elements

class NewAgeArticleParser : ArticleBodyParser() {

    private val mSiteBaseAddress = "http://www.newagebd.net"

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
        return NewAgeArticleParserInfo.FEATURED_IMAGE_SELECTOR
    }

    override fun getFeaturedImageLinkSelectorAttr(): String {
        return NewAgeArticleParserInfo.FEATURED_IMAGE_LINK_SELECTOR_ATTR
    }

    override fun getFeaturedImageCaptionSelector(): String {
        return NewAgeArticleParserInfo.FEATURED_IMAGE_CAPTION_SELECTOR
    }

    override fun getArticleFragmentBlocks(): Elements {

        return mDocument.select(NewAgeArticleParserInfo.ARTICLE_FRAGMENT_BLOCK_SELECTOR)
    }

    override fun getParagraphImageSelector(): String {
        return NewAgeArticleParserInfo.PARAGRAPH_IMAGE_SELECTOR
    }

    override fun getFeaturedImageCaptionSelectorAttr(): String? {
        return null
    }

    override fun getParagraphImageLinkSelectorAttr(): String {
        return NewAgeArticleParserInfo.PARAGRAPH_IMAGE_LINK_SELECTOR_ATTR
    }

    override fun getParagraphImageCaptionSelector(): String? {
        return null
    }

    override fun getParagraphImageCaptionSelectorAttr(): String {
        return NewAgeArticleParserInfo.PARAGRAPH_IMAGE_CAPTION_SELECTOR_ATTR
    }

    companion object {

        private val TAG = "TGArtLoader"
    }
}
