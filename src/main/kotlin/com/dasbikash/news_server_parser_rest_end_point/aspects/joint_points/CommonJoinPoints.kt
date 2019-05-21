package com.dasbikash.news_server_parser_rest_end_point.aspects.joint_points

import org.aspectj.lang.annotation.Pointcut

class CommonJoinPoints {

    @Pointcut("execution(* com.dasbikash.news_server_parser_rest_end_point.rest_resources..*EndPoint(..))")
    fun allControllersEndPoints() {}
}
