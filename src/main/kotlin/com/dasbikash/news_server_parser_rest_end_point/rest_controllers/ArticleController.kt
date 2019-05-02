package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.database.Article
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

    @GetMapping("/oldest")
    fun getOldestArticles(@RequestParam("article_count") articleCount:Int?):ResponseEntity<List<Article>>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(articleService.getOldestArticles(pageSize))
    }

    @GetMapping("/latest")
    fun getLatestArticles(@RequestParam("article_count") articleCount:Int?):ResponseEntity<List<Article>>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(articleService.getLatestArticles(pageSize))
    }

    @GetMapping("/after/{articleId}")
    fun getArticlesAfterGivenId(@PathVariable("articleId") articleId:String,
                                @RequestParam("article_count") articleCount:Int?)
            :ResponseEntity<List<Article>>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(articleService.getArticlesAfterGivenId(articleId,pageSize))
    }

    @GetMapping("/before/{articleId}")
    fun getArticlesBeforeGivenId(@PathVariable("articleId") articleId:String,
                                @RequestParam("article_count") articleCount:Int?)
            :ResponseEntity<List<Article>>{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(articleService.getArticlesBeforeGivenId(articleId,pageSize))
    }


}
