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

package com.dasbikash.news_server_parser_rest_end_point.parser.article_body_parsers.the_financial_express

internal object TheFinancialExpressArticleParserInfo {

    @JvmField val FEATURED_IMAGE_SELECTOR = ".card-image img"
    @JvmField val FEATURED_IMAGE_LINK_SELECTOR_ATTR = "data-src"
    @JvmField val FEATURED_IMAGE_CAPTION_SELECTOR = ".card-image .card-title-black"

    @JvmField val ARTICLE_FRAGMENT_BLOCK_SELECTOR = "#content-part p"

    @JvmField val PARAGRAPH_IMAGE_SELECTOR = "img"
    @JvmField val PARAGRAPH_IMAGE_LINK_SELECTOR_ATTR = "data-src"
    @JvmField val PARAGRAPH_IMAGE_CAPTION_SELECTOR_ATTR = "alt"

    @JvmField val ARTICLE_MODIFICATION_DATE_STRING_SELECTOR = "span.p3"
    @JvmField val ARTICLE_MODIFICATION_DATE_STRING_FORMATS = arrayOf("MMMM dd, yyyy HH:mm:ss","EEEE, dd MMMMM yyyy")//Tuesday, 14 August 2018
}
