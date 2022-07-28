package com.kakaopaysec.proxy.config.v3_proxyfactory.advice

import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation

class LogTraceAdvice(
    private val trace: LogTrace
): MethodInterceptor {

    override fun invoke(invocation: MethodInvocation): Any? {

        var status: TraceStatus? = null

        try {
            val method = invocation.method
            val message = "${method.declaringClass.simpleName}.${method.name}()"

            status = trace.begin(message)

            val result = invocation.proceed()

            trace.end(status)

            return result
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }
    }
}
