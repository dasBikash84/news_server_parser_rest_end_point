package com.dasbikash.news_server_parser_rest_end_point.services

import com.dasbikash.news_server_parser_rest_end_point.exceptions.IllegalRequestBodyException
import com.dasbikash.news_server_parser_rest_end_point.exceptions.TokenGenerationException
import com.dasbikash.news_server_parser_rest_end_point.model.database.AuthToken
import com.dasbikash.news_server_parser_rest_end_point.repositories.AuthTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthTokenService @Autowired constructor(val authTokenRepository: AuthTokenRepository){

    fun getNewAuthToken(): AuthToken {
        try {
            val newToken = AuthToken()
            authTokenRepository.save(newToken)
            return newToken
        }catch (ex:Exception){
            throw TokenGenerationException(ex)
        }
    }

    fun invalidateAuthToken(tokenId: String){
        try {
            val token = authTokenRepository.findById(tokenId).get()
            if (token.expiresOn < Date()){
                throw IllegalRequestBodyException()
            }
            token.expiresOn = Date()
            authTokenRepository.save(token)
        }catch (ex:Exception){
            throw IllegalRequestBodyException(ex)
        }
    }

    fun save(token: AuthToken) {
        authTokenRepository.save(token)
    }

    fun findToken(authToken: String?): AuthToken? {
        authToken?.let {
            authTokenRepository.findById(it).apply {
                if (isPresent){
                    return get()
                }
            }
        }
        return null
    }
}