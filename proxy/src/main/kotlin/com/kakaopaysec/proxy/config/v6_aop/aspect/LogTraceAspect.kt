package com.kakaopaysec.proxy.config.v6_aop.aspect

import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
class LogTraceAspect(
    private val trace: LogTrace
) {

    @Around("execution(* com.kakaopaysec.proxy.app..*(..))")
    fun execute(joinPoint: ProceedingJoinPoint): Any? {

        var status: TraceStatus? = null

        try {

            val message = joinPoint.signature.toShortString()

            status = trace.begin(message)

            val result = joinPoint.proceed()

            trace.end(status)

            return result
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }
    }
}
