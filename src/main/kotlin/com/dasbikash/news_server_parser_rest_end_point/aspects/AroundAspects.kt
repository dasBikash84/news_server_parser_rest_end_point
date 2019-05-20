package com.dasbikash.news_server_parser_rest_end_point.aspects

import com.dasbikash.news_server_parser_rest_end_point.model.OutputWrapper
import com.dasbikash.news_server_parser_rest_end_point.model.database.RestActivityLog
import com.dasbikash.news_server_parser_rest_end_point.repositories.RestActivityLogRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpServletRequest

@Configuration
@Aspect
open class AroundAspects(open var restActivityLogRepository: RestActivityLogRepository) {

//    open var logger = LoggerFactory.getLogger(this.javaClass)

    @Around("com.dasbikash.news_server_parser_rest_end_point.aspects.joint_points.CommonJoinPoints.allControllersEndPoints() && args(..,request)")
    @Throws(Throwable::class)
    fun aroundAdvice(proceedingJoinPoint: ProceedingJoinPoint,request: HttpServletRequest):Any {

        val startTime = System.currentTimeMillis()
        var result:Any?=null
        var exception:Exception?=null
        var outputEntityCount:Int?=null
        var acceptHeader:String?=null
        var userAgentHeader:String?=null
        try {
            result = proceedingJoinPoint.proceed()
        }catch (ex:Exception){
            exception=ex
        }

        if (result is ResponseEntity<*> && result.body is OutputWrapper){
            outputEntityCount = (result.body as OutputWrapper).getOutPutCount()
        }

        request.headerNames.asSequence().find { it.equals("accept") }?.let { acceptHeader= request.getHeader(it)}
        request.headerNames.asSequence().find { it.equals("user-agent") }?.let { userAgentHeader= request.getHeader(it) }

        val restActivityLog = RestActivityLog.getInstance(
                                                        proceedingJoinPoint,request,(System.currentTimeMillis() - startTime).toInt(),
                                                        exception?.let { it::class.java.canonicalName} ,outputEntityCount)

        restActivityLogRepository.save(restActivityLog)
        println(restActivityLog)

        exception?.let {
            throw it
        }
        return result!!
    }
}