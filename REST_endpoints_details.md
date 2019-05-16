## REST service End point details:

### For [`Language`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/Language.kt) data [endpoints](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/LanguageController.kt):

* For all supported `Language` settings data:
   * Type: `GET`
   * Path: http://localhost:8098/languages
   * Response: A [list](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/OutputWrappers.kt) of `Language` setting entries.
   
### For [`Country`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/Country.kt) data [endpoints](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/CountryController.kt):

* For all supported `Country` settings data:
   * Type: `GET`
   * Path: http://localhost:8098/countries
   * Response: A [list](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/OutputWrappers.kt) of `Country` setting entries.
   
### For [`Newspaper`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/Newspaper.kt) data [endpoints](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/NewsPaperController.kt):

* For all supported `Newspaper` settings data:
   * Type: `GET`
   * Path: http://localhost:8098/newspapers
   * Response: A [list](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/OutputWrappers.kt) of `Newspaper` setting entries.
   
* For `Newspaper` status change token generation: 
   * Type: `GET`
   * Path: http://localhost:8098/newspapers/request_newspaper_status_change_token_generation
   * Response: [`NewsPaperStatusChangeRequestFormat`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/NewsPaperStatusChangeRequest.kt) 
                 (and generated token will be emailed to authorized email addresses) or 
                 [`HttpStatus.INTERNAL_SERVER_ERROR`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpStatus.html#INTERNAL_SERVER_ERROR) for failure.

* For `Newspaper` status change request: 
    * Type: `POST`
    * Path: http://localhost:8098/newspapers/request_newspaper_status_change.
    * RequestBody : [`NewsPaperStatusChangeRequest`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/NewsPaperStatusChangeRequest.kt)
    * Response: Modified [`Newspaper`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/Newspaper.kt) 
      in case of success or [`HttpStatus.BAD_REQUEST`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpStatus.html#BAD_REQUEST) for invalid `NewsPaperStatusChangeRequest`.
   
   
### For [`Page`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/Page.kt) data [endpoints](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/PageController.kt):

* For all supported `Page` settings data:
   * Type: `GET`
   * Path: http://localhost:8098/pages
   * Response: A [list](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/OutputWrappers.kt) of `Page` setting entries.

* For all active pages of certain newspaper:
   * Type: `GET`
   * Path: http://localhost:8098/pages//newspaper_id/{newspaperId}
   * Path Param: newspaperId (Id of target News-Paper)
   * Response: A list of `Page` or [`HttpStatus.NOT_FOUND`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpStatus.html#NOT_FOUND) for invalid newspaperId.

### For [`PageGroup`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/PageGroup.kt) data [endpoints](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/PageGroupController.kt):

* For all supported `PageGroup` settings data:
   * Type: `GET`
   * Path: http://localhost:8098/page-groups
   * Response: A [list](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/OutputWrappers.kt) of `PageGroup` setting entries.   
   
### For [`Article`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/Article.kt) data [endpoints](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/ArticleController.kt):
   
* For latest `Article` data:
    * Type: `GET`
    * Address: http://localhost:8098/articles/latest?article_count={article_count}
    * Param: article_count(result-count) | optional | Default 10 | Max 50
    * Response: A [list](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/OutputWrappers.kt) of latest downloaded article data.
   
* For oldest `Article` data:
    * Type: `GET`
    * Address: http://localhost:8098/articles/oldest?article_count={article_count}
    * Param: article_count(result-count) | optional | Default 10 | Max 50
    * Response: A list of oldest downloaded `Article` data   
   
* For `Article` data after given id:
    * Type: `GET`
    * Address: http://localhost:8098/articles/after/{articleId}?article_count={article_count}
    * Path Param: `articleId`, id of last article.
    * Query Param: article_count(result-count) | optional | Default 10 | Max 50
    * Response: A list of `Article` data after given id or `HttpStatus.NOT_FOUND` for invalid articleId.
   
* For `Article` data before given id:
    * Type: `GET`
    * Address: http://localhost:8098/articles/before/{articleId}?article_count={article_count}
    * Path Param: `articleId`, id of last article.
    * Query Param: article_count(result-count) | optional | Default 10 | Max 50
    * Response: A list of `Article` data before given id or `HttpStatus.NOT_FOUND` for invalid articleId.

* For latest [`Article`] data of specific [`Page`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/Page.kt):
    * Type: `GET`
    * Address: http://localhost:8098/articles/page_id/{pageId}/latest?article_count={article_count}
    * Path Param: `pageId`, id of target Page.
    * Query Param: article_count(result-count) | optional | Default 10 | Max 50
    * Response: A list of latest `Article` data of target page or `HttpStatus.NOT_FOUND` for invalid pageId.

* For oldest [`Article`] data of specific `Page`:
    * Type: `GET`
    * Address: http://localhost:8098/articles/page_id/{pageId}/oldest?article_count={article_count}
    * Path Param: `pageId`, id of target Page.
    * Query Param: article_count(result-count) | optional | Default 10 | Max 50
    * Response: A list of latest `Article` data of target page or `HttpStatus.NOT_FOUND` for invalid pageId.

* For [`Article`] data of specific `Page` after a specific `Article`:
    * Type: `GET`
    * Address: http://localhost:8098/articles/page_id/{pageId}/after/article_id/{articleId}?article_count={article_count}
    * Path Param: `pageId`, id of target Page; articleId, id of target Article.
    * Query Param: article_count(result-count) | optional | Default 10 | Max 50
    * Response: A list of `Article` data of target *Page* after *Article* a specified id  or `HttpStatus.NOT_FOUND` for invalid pageId or articleId.

* For [`Article`] data of specific `Page` before a specific `Article`:
    * Type: `GET`
    * Address: http://localhost:8098/articles/page_id/{pageId}/before/article_id/{articleId}?article_count={article_count}
    * Path Param: `pageId`, id of target Page; articleId, id of target Article.
    * Query Param: article_count(result-count) | optional | Default 10 | Max 50
    * Response: A list of `Article` data of target *Page* before *Article* a specified id  or `HttpStatus.NOT_FOUND` for invalid pageId or articleId.

#### For [`General Log`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/GeneralLog.kt) data [endpoints](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/GeneralLogController.kt):

<a name="latest_log"></a>  
* For latest `General Log` entries:
   * Type: `GET`
   * Path: http://localhost:8098/general-logs?page-size={page-size}
   * Path Param: page-size(result-count) | optional | Default 10 | Max 50
   * Response: A `list` of latest *General Log* entries if any or `HttpStatus.NOT_FOUND`.
<a name="log_before_given_id"></a>    
* For *General Log* entries Before Given Id:
  * Type: `GET`
  * Path: http://localhost:8098/general-logs/before/{log-id}
  * Path Variable: *log-id* (id of last *General Log* entry)
  * Path Param: page-size(result-count) | optional | Default 10 | Max 50
  * Response: A list of latest *General Log* entries if any or `HttpStatus.NOT_FOUND` for invalid *log-id*.    
  
* For *General Log* entries After Given Id:
  * Type: `GET`
  * Path: http://localhost:8098/general-logs/after/{log-id}
  * Path Variable: *log-id* (id of last *General Log* entry)
  * Path Param: page-size(result-count) | optional | Default 10 | Max 50
  * Response: A list of latest *General Log* entries if any or `HttpStatus.NOT_FOUND` for invalid *log-id*.
  
<a name="request_log_delete_token_generation"></a>   
* For *General Log* entries deletion token generation request:
  * Type: `DELETE`
  * Path: http://localhost:8098/general-logs/request_log_delete_token_generation
  * Response: [`LogEntryDeleteRequestFormat`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/LogEntryDeleteRequest.kt) 
  (and generated token will be emailed to authorized email addresses) or 
  `HttpStatus.INTERNAL_SERVER_ERROR` for failure.
<a name="log_delete_request"></a>   
* For *General Log* entries deletion:
  * Type: `DELETE`
  * Path: http://localhost:8099/general-logs
  * RequestBody : [`LogEntryDeleteRequest`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/NewsPaperStatusChangeRequest.kt)
  * Response: List of deleted *General Log* entries or `HttpStatus.BAD_REQUEST` for invalid `LogEntryDeleteRequest`.
  
#### For [`Error Log`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/ErrorLog.kt) data [endpoints](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/ErrorLogController.kt):
* For latest `Error Log` entries:
    * Type: `GET`
    * Path: http://localhost:8098/error-logs?page-size={page-size}
    * Query Param & Response: Format same as [`General Log`](#latest_log) but for `Error Log`
     
* For *Error Log* entries Before Given Id:
    * Type: `GET`
    * Path: http://localhost:8098/error-logs/before/{log-id}?page-size={page-size}
    * Path Param, Query Param & Response: Format same as [`General Log`](#log_before_given_id) but for `Error Log`. 
     
* For *Error Log* entries After Given Id:
    * Type: `GET`
    * Path: http://localhost:8098/error-logs/after/{log-id}?page-size={page-size}
    * Path Param, Query Param & Response: Format same as [`General Log`](#log_before_given_id) but for `Error Log`.  
    
* For *Error Log* entries deletion token generation request:
    * Type: `DELETE`
    * Path: http://localhost:8098/error-logs/request_log_delete_token_generation
    * Response: Format same as [`General Log`](#request_log_delete_token_generation) but for `Error Log`. 
    
* For *Error Log* entries deletion:
    * Type: `DELETE`
    * Path: http://localhost:8098/error-logs 
    * Response: Format same as [`General Log`](#log_delete_request) but for `Error Log`. 
    
#### For [`Page Parsing History`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/PageParsingHistory.kt) data [endpoints](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/PageParsingHistoryController.kt):
* For latest `Page Parsing History` entries:
    * Type: `GET`
    * Path: http://localhost:8098/page-parsing-histories?page-size={page-size}
    * Query Param & Response: Format same as [`General Log`](#latest_log) but for `Page Parsing History`
     
* For *Page Parsing History* entries Before Given Id:
    * Type: `GET`
    * Path: http://localhost:8098/page-parsing-histories/before/{log-id}?page-size={page-size}
    * Path Param, Query Param & Response: Format same as [`General Log`](#log_before_given_id) but for `Page Parsing History`.
     
* For *Page Parsing History* entries After Given Id:
    * Type: `GET`
    * Path: http://localhost:8098/page-parsing-histories/after/{log-id}?page-size={page-size}
    * Path Param, Query Param & Response: Format same as [`General Log`](#log_before_given_id) but for `Page Parsing History`.  
    
* For *Page Parsing History* entries deletion token generation request:
    * Type: `DELETE`
    * Path: http://localhost:8098/page-parsing-histories/request_log_delete_token_generation
    * Response: Format same as [`General Log`](#request_log_delete_token_generation) but for `Page Parsing History`. 
    
* For *Page Parsing History* entries deletion:
    * Type: `DELETE`
    * Path: http://localhost:8098/page-parsing-histories 
    * Response: Format same as [`General Log`](#log_delete_request) but for `Page Parsing History`. 
