package com.kakaopaysec.proxy.config.v2_dynamicproxy.handler

import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import org.springframework.util.PatternMatchUtils
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class LogTraceFilterHandler(
    private val target: Any,
    private val trace: LogTrace,
    private val patterns: Array<String>
): InvocationHandler {

    override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any? {

        val methodName = method.name

        val hasArguments = args != null

        if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {
            return if (hasArguments) {
                method.invoke(target, args?.get(0).toString())
            } else {
                method.invoke(target)
            }
        }

        var status: TraceStatus? = null

        try {
            val message = "${method.declaringClass.simpleName}.${method.name}()"
            status = trace.begin(message)

            // target 호출
            val result = if (hasArguments) {
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
