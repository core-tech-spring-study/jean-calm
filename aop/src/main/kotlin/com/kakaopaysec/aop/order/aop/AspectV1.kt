package com.kakaopaysec.aop.order.aop

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

private val logger = KotlinLogging.logger {}

@Aspect
class AspectV1 {

    @Around("execution(* com.kakaopaysec.aop.order..*(..))")
    fun doLog(joinPoint: ProceedingJoinPoint): Any? {
        logger.info { "[log] {${joinPoint.signature}}" }
        return joinPoint.proceed()
    }
}
