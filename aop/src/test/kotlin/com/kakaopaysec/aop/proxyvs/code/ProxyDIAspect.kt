package com.kakaopaysec.aop.proxyvs.code

import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

private val logger = KotlinLogging.logger {}

@Aspect
class ProxyDIAspect {

    @Before("execution(* com.kakaopaysec.aop..*.*(..))")
    fun doTrace(joinPoint: JoinPoint) {
        logger.info { "[proxyDIAdvice] {${joinPoint.signature}}" }
    }
}
