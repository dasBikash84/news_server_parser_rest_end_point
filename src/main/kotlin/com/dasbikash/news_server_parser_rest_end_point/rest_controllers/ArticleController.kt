package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.Articles
import com.dasbikash.news_server_parser_rest_end_point.services.ArticleService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("articles")
open class ArticleController
constructor(open var articleService: ArticleService,
            open var restControllerUtils: RestControllerUtils) {

    @Value("\${article.default_page_size}")
    open var defaultPageSize: Int = 50

    @Value("\${article.max_page_size}")
    open var maxPageSize: Int = 100

    @GetMapping(value = arrayOf("/latest",""))
    open fun getLatestArticlesEndPoint(@RequestParam("article_count") articleCount:Int?,
                                       @Autowired request: HttpServletRequest):ResponseEntity<Articles>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(Articles(articleService.getLatestArticles(pageSize)))
    }

    @GetMapping("/oldest")
    open fun getOldestArticlesEndPoint(@RequestParam("article_count") articleCount:Int?,
                                       @Autowired request: HttpServletRequest):ResponseEntity<Articles>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(Articles(articleService.getOldestArticles(pageSize)))
    }

    @GetMapping("/after/article_id/{articleId}")
    open fun getArticlesAfterGivenIdEndPoint(@PathVariable("articleId") articleId:String,
                                            @RequestParam("article_count") articleCount:Int?,
                                             @Autowired request: HttpServletRequest)
            :ResponseEntity<Articles>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(Articles(articleService.getArticlesAfterGivenId(articleId, pageSize)))
    }

    @GetMapping("/before/article_id/{articleId}")
    open fun getArticlesBeforeGivenIdEndPoint(@PathVariable("articleId") articleId:String,
                                            @RequestParam("article_count") articleCount:Int?,
                                              @Autowired request: HttpServletRequest)
            :ResponseEntity<Articles>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(Articles(articleService.getArticlesBeforeGivenId(articleId, pageSize)))
    }



    @GetMapping("/page_id/{pageId}/latest")
    open fun getLatestArticlesForPageEndPoint(@PathVariable("pageId") pageId:String,
                                              @RequestParam("article_count") articleCount:Int?,
                                              @Autowired request: HttpServletRequest)
            :ResponseEntity<Articles>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(Articles(articleService.getLatestArticlesForPage(pageId, pageSize)))
    }

    @GetMapping("/page_id/{pageId}/oldest")
    open fun getOldestArticlesForPageEndPoint(@PathVariable("pageId") pageId: String,
                                              @RequestParam("article_count") articleCount: Int?,
                                              @Autowired request: HttpServletRequest)
            : ResponseEntity<Articles> {

        var pageSize = defaultPageSize
        articleCount?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(Articles(articleService.getOldestArticlesForPage(pageId, pageSize)))
    }

    @GetMapping("/page_id/{pageId}/after/article_id/{articleId}")
    open fun getArticlesAfterGivenIdForPageEndPoint(@PathVariable("articleId") articleId:String,
                                                    @PathVariable("pageId") pageId: String,
                                                    @RequestParam("article_count") articleCount: Int?,
                                                    @Autowired request: HttpServletRequest)
            : ResponseEntity<Articles> {

        var pageSize = defaultPageSize
        articleCount?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(Articles(articleService.getArticlesAfterGivenIdForPage(articleId, pageId, pageSize)))
    }

    @GetMapping("/page_id/{pageId}/before/article_id/{articleId}")
    open fun getArticlesBeforeGivenIdForPageEndPoint(@PathVariable("articleId") articleId:String,
                                                    @PathVariable("pageId") pageId: String,
                                                    @RequestParam("article_count") articleCount: Int?,
                                                     @Autowired request: HttpServletRequest)
            : ResponseEntity<Articles> {

        var pageSize = defaultPageSize
        articleCount?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtils.entityToResponseEntity(Articles(articleService.getArticlesBeforeGivenIdForPage(articleId, pageId, pageSize)))
    }


}
