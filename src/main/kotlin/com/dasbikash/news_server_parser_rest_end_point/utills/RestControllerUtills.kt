package com.dasbikash.news_server_parser_rest_end_point.utills

import com.dasbikash.news_server_parser_rest_end_point.exceptions.DataNotFoundException
import com.dasbikash.news_server_parser_rest_end_point.model.database.NsParserRestDbEntity
import org.springframework.http.ResponseEntity

object RestControllerUtills {
    fun <T : NsParserRestDbEntity> listEntityToResponseEntity(entiryList: List<T>): ResponseEntity<List<T>> {
        if (entiryList.isEmpty()) {
            throw DataNotFoundException()
        }
        return ResponseEntity.ok(entiryList)
    }

    fun <T : NsParserRestDbEntity> entityToResponseEntity(entity: T?): ResponseEntity<T> {
        if (entity == null) {
            throw DataNotFoundException()
        }
        return ResponseEntity.ok(entity)
    }
}