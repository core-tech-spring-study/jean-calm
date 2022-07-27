package com.kakaopaysec.proxy.cglib.code

import mu.KotlinLogging
import org.springframework.cglib.proxy.MethodInterceptor
import org.springframework.cglib.proxy.MethodProxy
import java.lang.reflect.Method

private val logger = KotlinLogging.logger {}

class TimeMethodInterceptor(
    private val target: Any
): MethodInterceptor {

    override fun intercept(any: Any?, method: Method?, args: Array<out Any>?, methodProxy: MethodProxy?): Any? {
        logger.info { "TimeProxy  실행" }
        val startTime = System.currentTimeMillis()

        val result = methodProxy?.invoke(target, args)
        val endTime = System.currentTimeMillis()

        val resultTime = endTime - startTime
        logger.info { "TimeProxy 종료, resultTime = ${resultTime}ms" }
        return result
    }
}
