## REST service from [parser](https://github.com/dasBikash84/ns_reloaded_backend_Parser_) app data:

This rest service addresses three issues:
1. Provides endpoints to [`coordinator`](https://github.com/dasBikash84/news_server_data_coordinator) app for
settings and article data.
2. Opens up endpoint interfaces for app status monitoring through log data.
3. Provides endpoint interfaces to project admin for settings modification.

#### This rest service supports following operations. For details check [here](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/REST_endpoints_details.md):
* [`GET`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/CountryController.kt) for
 [`Country`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/Country.kt)
 settings data.
* [`GET`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/LanguageController.kt) for
 [`Language`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/Language.kt)
 settings data.  
* [`GET`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/NewsPaperController.kt) for
 [`News-Paper`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/Newspaper.kt)
 settings data.  
* [`GET`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/PageController.kt) for
 [`Page`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/Page.kt)
 settings data.  
* [`GET`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/PageGroupController.kt) for
 [`Page-Group`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/PageGroup.kt)
 settings data.   
* [`GET`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/ArticleController.kt) for
 parsed [`Article`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/Article.kt)
 data.
* [`Get`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/GeneralLogController.kt) 
and Delete for [`General Log`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/GeneralLog.kt) 
entries.
* [`Get`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/ErrorLogController.kt) 
and Delete for [`Error Log`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/ErrorLog.kt) 
entries.
* [`Get`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/rest_controllers/PageParsingHistoryController.kt) 
and Delete for [`Page Parsing History`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/src/main/kotlin/com/dasbikash/news_server_parser_rest_end_point/model/database/PageParsingHistory.kt) 
entries.

##### All Delete operations has to be performed via OTP. Details are [`here`](https://github.com/dasBikash84/news_server_parser_rest_end_point/blob/master/REST_endpoints_details.md)