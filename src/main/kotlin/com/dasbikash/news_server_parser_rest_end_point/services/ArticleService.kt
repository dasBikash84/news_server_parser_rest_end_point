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
        return filterArticlesWithNullPublicationTime(articleRepository.findOldest(pageSize))
    }

    fun getLatestArticles(pageSize: Int): List<Article> {
        return filterArticlesWithNullPublicationTime(articleRepository.findLatest(pageSize))
    }

    fun getArticlesAfterGivenId(articleId: String, pageSize: Int): List<Article> {
        val currentArticle = articleRepository.findByArticleId(articleId)
        if (currentArticle == null){
            throw DataNotFoundException()
        }
        val articles = articleRepository.getArticlesAfterGivenId(currentArticle.getSerial()!!,pageSize)
        return filterArticlesWithNullPublicationTime(articles)
    }

    fun getArticlesBeforeGivenId(articleId: String, pageSize: Int): List<Article> {
        val currentArticle = articleRepository.findByArticleId(articleId)
        if (currentArticle == null){
            throw DataNotFoundException()
        }
        val articles = articleRepository.getArticlesBeforeGivenId(currentArticle.getSerial()!!,pageSize)
        return filterArticlesWithNullPublicationTime(articles)
    }

    fun getLatestArticlesForPage(pageId: String, pageSize: Int): List<Article> {
        val pageOptional = pageRepository.findById(pageId)
        if (!pageOptional.isPresent){
            throw DataNotFoundException()
        }
        val articles = articleRepository.findLatestByPageId(pageId,pageSize)
        return filterArticlesWithNullPublicationTime(articles)
    }

    fun getOldestArticlesForPage(pageId: String, pageSize: Int): List<Article> {
        val pageOptional = pageRepository.findById(pageId)
        if (!pageOptional.isPresent){
            throw DataNotFoundException()
        }
        val articles = articleRepository.findOldestByPageId(pageId,pageSize)
        return filterArticlesWithNullPublicationTime(articles)
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
        val articles = articleRepository.getArticlesAfterGivenIdForPage(currentArticle.getSerial()!!,pageId,pageSize)
        return filterArticlesWithNullPublicationTime(articles)
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
        val articles = articleRepository.getArticlesBeforeGivenIdForPage(currentArticle.getSerial()!!,pageId,pageSize)
        return filterArticlesWithNullPublicationTime(articles)
    }

    private fun filterArticlesWithNullPublicationTime(articles: List<Article>) =
            articles.filter { it.getPublicationTS()!=null || it.getModificationTS()!=null }.toList()
}