package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.DataNotFoundException
import com.dasbikash.news_server_parser_rest_end_point.model.database.Article
import com.dasbikash.news_server_parser_rest_end_point.repositories.ArticleRepository
import com.dasbikash.news_server_parser_rest_end_point.repositories.PageRepository
import org.springframework.stereotype.Service

@Service
open class ArticleService
 constructor(open var pageRepository: PageRepository,
             open var articleRepository: ArticleRepository) {

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
        return articleRepository.getArticlesAfterGivenId(currentArticle.getSerial()!!,pageSize)
    }

    fun getArticlesBeforeGivenId(articleId: String, pageSize: Int): List<Article> {
        val currentArticle = articleRepository.findByArticleId(articleId)
        if (currentArticle == null){
            throw DataNotFoundException()
        }
        return articleRepository.getArticlesBeforeGivenId(currentArticle.getSerial()!!,pageSize)
    }

    fun getLatestArticlesForPage(pageId: String, pageSize: Int): List<Article> {
        val pageOptional = pageRepository.findById(pageId)
        if (!pageOptional.isPresent){
            throw DataNotFoundException()
        }
        return articleRepository.findLatestByPageId(pageId,pageSize)
    }

    fun getOldestArticlesForPage(pageId: String, pageSize: Int): List<Article> {
        val pageOptional = pageRepository.findById(pageId)
        if (!pageOptional.isPresent){
            throw DataNotFoundException()
        }
        return articleRepository.findOldestByPageId(pageId,pageSize)
    }

    fun getArticlesAfterGivenIdForPage(articleId: String, pageId: String, pageSize: Int): List<Article> {
        val pageOptional = pageRepository.findById(pageId)
        if (!pageOptional.isPresent){
            throw DataNotFoundException()
        }
        val currentArticle = articleRepository.findByArticleId(articleId)
        if (currentArticle == null){
            throw DataNotFoundException()
        }
        return articleRepository.getArticlesAfterGivenIdForPage(currentArticle.getSerial()!!,pageId,pageSize)
    }

    fun getArticlesBeforeGivenIdForPage(articleId: String, pageId: String, pageSize: Int): List<Article> {
        val pageOptional = pageRepository.findById(pageId)
        if (!pageOptional.isPresent){
            throw DataNotFoundException()
        }
        val currentArticle = articleRepository.findByArticleId(articleId)
        if (currentArticle == null){
            throw DataNotFoundException()
        }
        return articleRepository.getArticlesBeforeGivenIdForPage(currentArticle.getSerial()!!,pageId,pageSize)
    }
}