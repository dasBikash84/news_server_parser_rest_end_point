package com.dasbikash.news_server_parser_rest_end_point.repositories

import com.dasbikash.news_server_parser_rest_end_point.model.database.Article
import com.dasbikash.news_server_parser_rest_end_point.model.database.DatabaseTableNames
import org.springframework.data.mongodb.repository.CountQuery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
//import org.springframework.data.jpa.repository.JpaRepository
//import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*
import java.util.stream.Stream

interface ArticleRepository : MongoRepository<Article, String>{

    fun getByOrderByModifiedAsc():Stream<Article>
    fun getByOrderByModifiedDesc():Stream<Article>
    fun getByPageIdOrderByModifiedAsc(pageId: String):Stream<Article>
    fun getByPageIdOrderByModifiedDesc(pageId: String):Stream<Article>

    @Query(value = "{'modified' : {'\$lt':?0}}")
    fun getArticlesBeforeGivenTime(time:Long): Stream<Article>
    @Query(value = "{'modified' : {'\$gt':?0}}")
    fun getArticlesAfterGivenTime(time:Long): Stream<Article>

//    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_TABLE_NAME} where serial > :articleSerial AND pageId=:pageId and articleText is not null  order by serial ASC limit :pageSize",
//            nativeQuery = true)
//    fun getArticlesAfterGivenIdForPage(articleSerial: Int, pageId: String, pageSize: Int): List<Article>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_TABLE_NAME} where serial < :articleSerial AND pageId=:pageId and articleText is not null  order by serial DESC limit :pageSize",
            nativeQuery = true)
    fun getArticlesBeforeGivenIdForPage(articleSerial: Int, pageId: String, pageSize: Int): List<Article>

    @CountQuery(value = "{'pageId' : ?0}")
    fun getArticleCountForPage(pageId: String): Int

    @Query(value = "{'pageId' : ?0}",fields = "{'publicationTS':1,'_id':0}")
    fun getArticlePublicationTSForPage(pageId: String): List<Date?>

    @Query(value = "{'pageId' : ?0}",fields = "{'modificationTS':1,'_id':0}")
    fun getArticleModificationTSForPage(pageId: String): List<Date?>
}