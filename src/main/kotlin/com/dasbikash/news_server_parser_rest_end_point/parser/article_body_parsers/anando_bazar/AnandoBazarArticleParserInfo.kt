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

package com.dasbikash.news_server_parser_rest_end_point.parser.article_body_parsers.anando_bazar

internal object AnandoBazarArticleParserInfo {

    @JvmField val FEATURED_IMAGE_SELECTOR = ".textwrap_left img,.image img"
    @JvmField val FEATURED_IMAGE_LINK_SELECTOR = "src"
    @JvmField val FEATURED_IMAGE_CAPTION_SELECTOR = ".text_below_img"

    @JvmField val ARTICLE_FRAGMENT_BLOCK_SELECTOR = arrayOf("#textbody p", ".item .zoom","#story_container p")

    @JvmField val PARAGRAPH_IMAGE_SELECTOR = arrayOf("img", "a","img")

    @JvmField val PARAGRAPH_IMAGE_LINK_SELECTOR_ATTR = arrayOf("src", "href","src")
    @JvmField val PARAGRAPH_IMAGE_CAPTION_SELECTOR_ATTR = arrayOf("alt", "data-caption", "alt")

    @JvmField val ARTICLE_MODIFICATION_DATE_STRING_SELECTOR =
            "[class='abp-story-date-div2'],[class='abp-created-date hidden-sm hidden-xs'],[class='abp-story-date-div']"

    @JvmField val ARTICLE_MODIFICATION_DATE_STRING_FORMATS = arrayOf("dd MMM, yyyy, HH:mm:ss","dd MMM , yyyy, HH:mm:ss", "dd, MMM, yyyy HH:mm:ss")
}
