package com.kakaopaysec.proxy.pureproxy.proxy.common.advice

import mu.KotlinLogging
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation

private val logger = KotlinLogging.logger {}

class TimeAdvice: MethodInterceptor {

    override fun invoke(invocation: MethodInvocation): Any? {
       logger.info { "TimeProxy  실행" }
        val startTime = System.currentTimeMillis()

        val result = invocation.proceed()
        val endTime = System.currentTimeMillis()

        val resultTime = endTime - startTime
        logger.info { "TimeProxy 종료, resultTime = ${resultTime}ms" }
        return result
    }
}
