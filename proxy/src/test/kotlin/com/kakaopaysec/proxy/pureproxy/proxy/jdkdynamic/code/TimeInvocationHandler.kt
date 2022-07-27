package com.kakaopaysec.proxy.pureproxy.proxy.jdkdynamic.code

import mu.KotlinLogging
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

private val logger = KotlinLogging.logger {}

class TimeInvocationHandler(
    private val target: Any
): InvocationHandler {

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any {
        logger.info { "TimeProxy  실행" }
        val startTime = System.currentTimeMillis()

        val result = method.invoke(target)
        val endTime = System.currentTimeMillis()

        val resultTime = endTime - startTime
        logger.info { "TimeProxy 종료, resultTime = ${resultTime}ms" }
        return result
    }
}
