package com.dasbikash.news_server_parser_rest_end_point

import org.slf4j.LoggerFactory
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component("DisplayNamePostProcessor")
class DisplayNamePostProcessor : BeanPostProcessor {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Throws(BeansException::class)
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (beanName.contains("Controller",true) || beanName.contains("Service",true)) {
            logger.info("postProcessBeforeInitialization {} ",beanName)
        }
        return bean
    }

    @Throws(BeansException::class)
    override fun postProcessAfterInitialization(bean: Any, beanName: String?): Any? {

        if (beanName!!.contains("Controller",true) || beanName.contains("Service",true)) {
            logger.info("postProcessAfterInitialization {} ", beanName)
        }
        return bean
    }
}
