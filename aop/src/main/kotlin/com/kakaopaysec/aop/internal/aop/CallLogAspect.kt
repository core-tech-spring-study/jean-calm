package com.kakaopaysec.aop.internal.aop

import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

private val logger = KotlinLogging.logger {}

@Aspect
class CallLogAspect {

    @Before("execution(* com.kakaopaysec.aop.internal..*.*(..))")
    fun doLog(joinPoint: JoinPoint) {
        logger.info { "aop = {${joinPoint.signature}}" }
    }
}
