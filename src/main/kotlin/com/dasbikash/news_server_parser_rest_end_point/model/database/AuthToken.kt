package com.dasbikash.news_server_parser_rest_end_point.model.database

import com.google.firebase.database.Exclude
import org.springframework.data.annotation.*
import java.util.*
//import javax.persistence.Entity
//import javax.persistence.Id
//import javax.persistence.Table
//import javax.persistence.Transient

//@Entity
//@Table(name = DatabaseTableNames.AUTH_TOKEN_TABLE_NAME)
class AuthToken(){
    @Id
    val token:String = UUID.randomUUID().toString()
    var expiresOn:Date

    init{
        val calander = Calendar.getInstance()
        calander.add(Calendar.MINUTE,TOKEN_LIFE_TIME_MINUTES)
        expiresOn = calander.time
    }

    @Transient
    fun hasExpired():Boolean{
        return System.currentTimeMillis() > expiresOn.time
    }

    fun makeExpired(){
        expiresOn = Date()
    }

    companion object{
        private const val TOKEN_LIFE_TIME_MINUTES = 5
    }
}

data class TokenGenerationRequest(var timeStampMs:Long? = null){

    @Exclude
    fun isValid():Boolean{
        timeStampMs?.let {
            return (System.currentTimeMillis() - it) < MAX_ALLOWED_AGE_MS
        }
        return false
    }

    companion object{
        private const val MAX_ALLOWED_AGE_MINUTE = 5
        private const val MAX_ALLOWED_AGE_MS = MAX_ALLOWED_AGE_MINUTE*60*1000L
    }
}