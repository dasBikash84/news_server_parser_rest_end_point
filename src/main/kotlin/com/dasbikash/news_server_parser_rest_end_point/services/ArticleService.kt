package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.DataNotFoundException
import com.dasbikash.news_server_parser_rest_end_point.model.database.Article
import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import com.dasbikash.news_server_parser_rest_end_point.repositories.ArticleRepository
import com.dasbikash.news_server_parser_rest_end_point.repositories.PageRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
open class ArticleService
 constructor(open var pageRepository: PageRepository,
             open var articleRepository: ArticleRepository) {

    fun getOldestArticles(pageSize: Int): List<Article> {
        val pageable = PageRequest.of(0,pageSize, Sort.by(Article.MODIFIED_PROPERTY_NAME).ascending())
        return filterInvalidArticles(articleRepository.findAll(pageable).content)
    }

    fun getLatestArticles(pageSize: Int): List<Article> {
        val pageable = PageRequest.of(0,pageSize, Sort.by(Article.MODIFIED_PROPERTY_NAME).descending())
        return filterInvalidArticles(articleRepository.findAll(pageable).content)
    }

    fun getArticlesAfterGivenId(articleId: String, pageSize: Int): List<Article> {
        val currentArticleOptional = articleRepository.findById(articleId)
        if (!currentArticleOptional.isPresent){
            throw DataNotFoundException()
        }
        val currentArticle = currentArticleOptional.get()
        val articles = articleRepository.getArticlesAfterGivenId(currentArticleOptional.getSerial()!!,pageSize)
        return filterInvalidArticles(articles)
    }

    fun getArticlesBeforeGivenId(articleId: String, pageSize: Int): List<Article> {
        val currentArticle = articleRepository.findByArticleId(articleId)
        if (currentArticle == null){
            throw DataNotFoundException()
        }
        val articles = articleRepository.getArticlesBeforeGivenId(currentArticle.getSerial()!!,pageSize)
        return filterInvalidArticles(articles)
    }

    fun getLatestArticlesForPage(pageId: String, pageSize: Int): List<Article> {
        val pageOptional = pageRepository.findById(pageId)
        if (!pageOptional.isPresent){
            throw DataNotFoundException()
        }
        val articles = articleRepository.findLatestByPageId(pageId,pageSize)
        return filterInvalidArticles(articles)
    }

    fun getOldestArticlesForPage(pageId: String, pageSize: Int): List<Article> {
        val pageOptional = pageRepository.findById(pageId)
        if (!pageOptional.isPresent){
            throw DataNotFoundException()
        }
        val articles = articleRepository.findOldestByPageId(pageId,pageSize)
        return filterInvalidArticles(articles)
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
        return filterInvalidArticles(articles)
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
        return filterInvalidArticles(articles)
    }

    private fun filterInvalidArticles(articles: List<Article>):List<Article> {
        val filteredArticles = articles.filter { it.getPublicationTS() != null || it.getModificationTS() != null || it.articleText==null }.toList()
        if (filteredArticles.isEmpty()){
            throw DataNotFoundException()
        }
        return filteredArticles
    }

    fun getArticleCountForPage(page: Page): Int{
        return articleRepository.getArticleCountForPage(page.id)
    }

    fun getArticlePublicationTSForPage(page: Page): List<Date?> {
        return articleRepository.getArticlePublicationTSForPage(page.id)
    }

    fun getArticleModificationTSForPage(page: Page): List<Date?> {
        return articleRepository.getArticleModificationTSForPage(page.id)
    }

    fun findArticleById(articleId: String):Article?{
        return articleRepository.findByArticleId(articleId)
    }

    fun save(article: Article):Article {
        return articleRepository.save(article)
    }
}