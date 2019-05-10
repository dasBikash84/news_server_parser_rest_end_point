package com.dasbikash.news_server_parser_rest_end_point.model.database

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = DatabaseTableNames.AUTH_TOKEN_TABLE_NAME)
class AuthToken(){
    @Id
    val token:String = UUID.randomUUID().toString()
    var expiresOn:Date

    init{
        val calander = Calendar.getInstance()
        calander.add(Calendar.MINUTE,TOKEN_LIFE_TIME_MINUTES)
        expiresOn = calander.time
    }
    companion object{
        private const val TOKEN_LIFE_TIME_MINUTES = 5
    }
}