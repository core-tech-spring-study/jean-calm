package com.kakaopaysec.proxy.config.v2_dynamicproxy.handler

import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import mu.KotlinLogging
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

private val logger = KotlinLogging.logger {}

class LogTraceBasicHandler(
    private val target: Any,
    private val trace: LogTrace,
): InvocationHandler {

    override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any? {

        var status: TraceStatus? = null

        try {
            val message = "${method.declaringClass.simpleName}.${method.name}()"
            status = trace.begin(message)

            // target 호출
            val result = if (args != null) {
                method.invoke(target, args?.get(0).toString())
            } else {
                method.invoke(target)
            }

            trace.end(status)

            return result
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }
    }
}
