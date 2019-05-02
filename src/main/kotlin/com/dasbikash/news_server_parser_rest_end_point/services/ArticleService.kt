package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.DataNotFoundException
import com.dasbikash.news_server_parser_rest_end_point.model.database.Article
import com.dasbikash.news_server_parser_rest_end_point.repositories.ArticleRepository
import com.dasbikash.news_server_parser_rest_end_point.repositories.PageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArticleService
@Autowired constructor(val pageRepository: PageRepository, val articleRepository: ArticleRepository) {

    fun getOldestArticles(pageSize: Int): List<Article> {
        return articleRepository.findOldest(pageSize)
    }

    fun getLatestArticles(pageSize: Int): List<Article> {
        return articleRepository.findLatest(pageSize)
    }

    fun getArticlesAfterGivenId(articleId: String, pageSize: Int): List<Article> {
        val currentArticle = articleRepository.findByArticleId(articleId)
        if (currentArticle == null){
            throw DataNotFoundException()
        }
        return articleRepository.getArticlesAfterGivenId(currentArticle.serial!!,pageSize)
    }

    fun getArticlesBeforeGivenId(articleId: String, pageSize: Int): List<Article> {
        val currentArticle = articleRepository.findByArticleId(articleId)
        if (currentArticle == null){
            throw DataNotFoundException()
        }
        return articleRepository.getArticlesBeforeGivenId(currentArticle.serial!!,pageSize)
    }
}