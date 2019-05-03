package com.dasbikash.news_server_parser_rest_end_point.repositories

import com.dasbikash.news_server_parser_rest_end_point.model.database.Article
import com.dasbikash.news_server_parser_rest_end_point.model.database.DatabaseTableNames
import com.dasbikash.news_server_parser_rest_end_point.model.database.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ArticleRepository : JpaRepository<Article, String>{

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_TABLE_NAME} order by serial ASC limit :articleCount",
            nativeQuery = true)
    fun findOldest(articleCount: Int): List<Article>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_TABLE_NAME} order by serial DESC limit :articleCount",
            nativeQuery = true)
    fun findLatest(@Param("articleCount") articleCount:Int):List<Article>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_TABLE_NAME} where serial > :articleSerial order by serial ASC limit :articleCount",
            nativeQuery = true)
    fun getArticlesAfterGivenId(articleSerial: Int, articleCount: Int): List<Article>

    fun findByArticleId(articleId:String):Article?

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_TABLE_NAME} where serial < :articleSerial order by serial DESC limit :articleCount",
            nativeQuery = true)
    fun getArticlesBeforeGivenId(articleSerial: Int, articleCount: Int): List<Article>


    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_TABLE_NAME} where pageId=:pageId order by serial DESC limit :pageSize",
            nativeQuery = true)
    fun findLatestByPageId(pageId: String, pageSize: Int): List<Article>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_TABLE_NAME} where pageId=:pageId order by serial ASC limit :pageSize",
            nativeQuery = true)
    fun findOldestByPageId(pageId: String, pageSize: Int): List<Article>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_TABLE_NAME} where serial > :articleSerial AND pageId=:pageId  order by serial ASC limit :pageSize",
            nativeQuery = true)
    fun getArticlesAfterGivenIdForPage(articleSerial: Int, pageId: String, pageSize: Int): List<Article>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_TABLE_NAME} where serial < :articleSerial AND pageId=:pageId  order by serial DESC limit :pageSize",
            nativeQuery = true)
    fun getArticlesBeforeGivenIdForPage(articleSerial: Int, pageId: String, pageSize: Int): List<Article>
}