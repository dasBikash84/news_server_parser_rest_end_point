## REST service End point details:

### For \"Language data\"

* For all supported language settings:
   * Address: http://localhost:8098/languages
   * Param: N/A
   * Response: A list of language setting entries.
   
### For \"Country data\"

* For all supported Country settings:
   * Address: http://localhost:8098/countries
   * Param: N/A
   * Response: A list of Country setting entries.
   
### For \"Newspaper data\"

* For all supported Newspaper settings:
   * Address: http://localhost:8098/newspapers
   * Param: N/A
   * Response: A list of supported Newspaper setting entries.
   
### For \"Page data\"

* For all supported Page settings:
   * Address: http://localhost:8098/pages
   * Param: N/A
   * Response: A list of all supported Page setting entries.
   
### For \"Article data\"
   
* For Oldest Article data:
   * Address: http://localhost:8098/articles/oldest?article_count={resultSize}
   * Param: \"resultSize\" => default 50 if no value/<=0 provided, max = 200.
   * Response: A list of oldest downloaded article data
   
* For latest Article data:
   * Address: http://localhost:8098/articles/latest?article_count={resultSize}
   * Param: \"resultSize\" => default 50 if no value/<=0 provided, max = 200.
   * Response: A list of latest downloaded article data
   
* For Article data after given id:
   * Address: http://localhost:8098/articles/after/{articleId}?article_count={resultSize}
   * Param: \"resultSize\" => default 50 if no value/<=0 provided, max = 200.
   * Response: A list of article data after given id
   
* For Article data before given id:
   * Address: http://localhost:8098/articles/before/{articleId}?article_count={resultSize}
   * Param: \"resultSize\" => default 50 if no value/<=0 provided, max = 200.
   * Response: A list of article data before given id
   
   
#### In case of invalid param/end of data \"404 Not Found\" status will be returned.

 
