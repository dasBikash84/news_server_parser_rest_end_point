package com.dasbikash.news_server_parser_rest_end_point.rest_controllers

import com.dasbikash.news_server_parser_rest_end_point.model.Articles
import com.dasbikash.news_server_parser_rest_end_point.model.RequestDetailsBean
import com.dasbikash.news_server_parser_rest_end_point.services.ArticleService
import com.dasbikash.news_server_parser_rest_end_point.utills.RestControllerUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("articles")
@Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
@Consumes(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
@Component
open class ArticleResource
constructor(open var articleService: ArticleService?=null,
            open var restControllerUtils: RestControllerUtils?=null) {

    @Value("\${article.default_page_size}")
    open var defaultPageSize: Int = 50

    @Value("\${article.max_page_size}")
    open var maxPageSize: Int = 100

    @GET
    @Path("/latest")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getLatestArticlesEndPoint(@QueryParam("article_count") articleCount:Int?,
                                       @BeanParam requestDetails: RequestDetailsBean): Response {

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(Articles(articleService!!.getLatestArticles(pageSize)))
    }

    @GET
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getLatestArticlesEmptyPath(@QueryParam("article_count") articleCount:Int?,
                                        @BeanParam requestDetails: RequestDetailsBean): Response{
        return getLatestArticlesEndPoint(articleCount, requestDetails)
    }

    @GET
    @Path("/oldest")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getOldestArticlesEndPoint(@QueryParam("article_count") articleCount:Int?,
                                       @BeanParam requestDetails: RequestDetailsBean):Response{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(Articles(articleService!!.getOldestArticles(pageSize)))
    }

    @GET
    @Path("/after/article_id/{articleId}")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getArticlesAfterGivenIdEndPoint(@PathParam("articleId") articleId:String,
                                             @QueryParam("article_count") articleCount:Int?,
                                             @BeanParam requestDetails: RequestDetailsBean)
            :Response{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(Articles(articleService!!.getArticlesAfterGivenId(articleId, pageSize)))
    }

    @GET
    @Path("/before/article_id/{articleId}")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getArticlesBeforeGivenIdEndPoint(@PathParam("articleId") articleId:String,
                                              @QueryParam("article_count") articleCount:Int?,
                                              @BeanParam requestDetails: RequestDetailsBean)
            :Response{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(Articles(articleService!!.getArticlesBeforeGivenId(articleId, pageSize)))
    }

    @GET
    @Path("/page_id/{pageId}/latest")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getLatestArticlesForPage(@PathParam("pageId") pageId:String,
                                      @QueryParam("article_count") articleCount:Int?,
                                      @BeanParam requestDetails: RequestDetailsBean)
            :Response{

        var pageSize = defaultPageSize
        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(Articles(articleService!!.getLatestArticlesForPage(pageId, pageSize)))
    }

    @GET
    @Path("/page_id/{pageId}/oldest")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getOldestArticlesForPageEndPoint(@PathParam("pageId") pageId: String,
                                              @QueryParam("article_count") articleCount: Int?,
                                              @BeanParam requestDetails: RequestDetailsBean)
            : Response {

        var pageSize = defaultPageSize
        articleCount?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(Articles(articleService!!.getOldestArticlesForPage(pageId, pageSize)))
    }

    @GET
    @Path("/page_id/{pageId}/after/article_id/{articleId}")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getArticlesAfterGivenIdForPageEndPoint(@PathParam("articleId") articleId:String,
                                                    @PathParam("pageId") pageId: String,
                                                    @QueryParam("article_count") articleCount: Int?,
                                                    @BeanParam requestDetails: RequestDetailsBean)
            : Response{

        var pageSize = defaultPageSize
        articleCount?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(Articles(articleService!!.getArticlesAfterGivenIdForPage(articleId, pageId, pageSize)))
    }

    @GET
    @Path("/page_id/{pageId}/before/article_id/{articleId}")
    @Produces(value = arrayOf(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
    open fun getArticlesBeforeGivenIdForPage(@PathParam("articleId") articleId:String,
                                             @PathParam("pageId") pageId: String,
                                             @QueryParam("article_count") articleCount: Int?,
                                             @BeanParam requestDetails: RequestDetailsBean)
            : Response {

        var pageSize = defaultPageSize
        articleCount?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtils!!.entityToResponseEntity(Articles(articleService!!.getArticlesBeforeGivenIdForPage(articleId, pageId, pageSize)))
    }

}
