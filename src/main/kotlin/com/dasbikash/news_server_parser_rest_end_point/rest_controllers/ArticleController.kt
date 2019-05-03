package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.database.Article
import com.dasbikash.news_server_parser_rest_end_point.model.database.Articles
import com.dasbikash.news_server_parser_rest_end_point.services.ArticleService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("articles")
class ArticleController @Autowired
constructor(val articleService: ArticleService) {

    @Value("\${article.default_page_size}")
    var defaultPageSize: Int = 50

    @Value("\${article.max_page_size}")
    var maxPageSize: Int = 100

    @GetMapping(value = arrayOf("/latest",""))
    fun getLatestArticles(@RequestParam("article_count") articleCount:Int?):ResponseEntity<Articles>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return RestControllerUtills.entityToResponseEntity(Articles(articleService.getLatestArticles(pageSize)))
    }

    @GetMapping("/oldest")
    fun getOldestArticles(@RequestParam("article_count") articleCount:Int?):ResponseEntity<Articles>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return RestControllerUtills.entityToResponseEntity(Articles(articleService.getOldestArticles(pageSize)))
    }

    @GetMapping("/after/article_id/{articleId}")
    fun getArticlesAfterGivenId(@PathVariable("articleId") articleId:String,
                                @RequestParam("article_count") articleCount:Int?)
            :ResponseEntity<Articles>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return RestControllerUtills.entityToResponseEntity(Articles(articleService.getArticlesAfterGivenId(articleId,pageSize)))
    }

    @GetMapping("/before/article_id/{articleId}")
    fun getArticlesBeforeGivenId(@PathVariable("articleId") articleId:String,
                                @RequestParam("article_count") articleCount:Int?)
            :ResponseEntity<Articles>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return RestControllerUtills.entityToResponseEntity(Articles(articleService.getArticlesBeforeGivenId(articleId,pageSize)))
    }



    @GetMapping("/page_id/{pageId}/latest")
    fun getLatestArticlesForPage(@PathVariable("pageId") pageId:String, @RequestParam("article_count") articleCount:Int?)
            :ResponseEntity<Articles>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return RestControllerUtills.entityToResponseEntity(Articles(articleService.getLatestArticlesForPage(pageId,pageSize)))
    }

    @GetMapping("/page_id/{pageId}/oldest")
    fun getOldestArticlesForPage(@PathVariable("pageId") pageId: String, @RequestParam("article_count") articleCount: Int?)
            : ResponseEntity<Articles> {

        var pageSize = defaultPageSize
        articleCount?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return RestControllerUtills.entityToResponseEntity(Articles(articleService.getOldestArticlesForPage(pageId, pageSize)))
    }

    @GetMapping("/page_id/{pageId}/after/article_id/{articleId}")
    fun getArticlesAfterGivenIdForPage(@PathVariable("articleId") articleId:String,
                                       @PathVariable("pageId") pageId: String,
                                       @RequestParam("article_count") articleCount: Int?)
            : ResponseEntity<Articles> {

        var pageSize = defaultPageSize
        articleCount?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return RestControllerUtills.entityToResponseEntity(Articles(articleService.getArticlesAfterGivenIdForPage(articleId,pageId, pageSize)))
    }

    @GetMapping("/page_id/{pageId}/before/article_id/{articleId}")
    fun getArticlesBeforeGivenIdForPage(@PathVariable("articleId") articleId:String,
                                       @PathVariable("pageId") pageId: String,
                                       @RequestParam("article_count") articleCount: Int?)
            : ResponseEntity<Articles> {

        var pageSize = defaultPageSize
        articleCount?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return RestControllerUtills.entityToResponseEntity(Articles(articleService.getArticlesBeforeGivenIdForPage(articleId,pageId, pageSize)))
    }


}
