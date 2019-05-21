package com.dasbikash.news_server_parser_rest_end_point.aspects

import com.dasbikash.news_server_parser_rest_end_point.model.OutputWrapper
import com.dasbikash.news_server_parser_rest_end_point.model.RequestDetailsBean
import com.dasbikash.news_server_parser_rest_end_point.model.database.RestActivityLog
import com.dasbikash.news_server_parser_rest_end_point.repositories.RestActivityLogRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.context.annotation.Configuration
import javax.ws.rs.core.Response

@Configuration
@Aspect
open class AroundAspects(open var restActivityLogRepository: RestActivityLogRepository) {

    @Around("com.dasbikash.news_server_parser_rest_end_point.aspects.joint_points.CommonJoinPoints.allControllersEndPoints() && args(..,requestDetails)")
    @Throws(Throwable::class)
    fun aroundAdvice(proceedingJoinPoint: ProceedingJoinPoint, requestDetails: RequestDetailsBean): Any {

        val startTime = System.currentTimeMillis()
        var result: Any? = null
        var exception: Exception? = null
        var outputEntityCount: Int? = null
        try {
            result = proceedingJoinPoint.proceed()
        } catch (ex: Exception) {
            exception = ex
        }

        if (result is Response && result.entity is OutputWrapper) {
            outputEntityCount = (result.entity as OutputWrapper).getOutPutCount()
        }

        val restActivityLog = RestActivityLog.getInstance(
                proceedingJoinPoint, (System.currentTimeMillis() - startTime).toInt(),
                exception?.let { it::class.java.canonicalName }, outputEntityCount, requestDetails)

        restActivityLogRepository.save(restActivityLog)

        println(restActivityLog)

        exception?.let {
            throw it
        }
        return result!!
    }
}