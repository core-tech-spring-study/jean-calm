package com.kakaopaysec.aop.exam.aop

import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import java.util.*

private val logger = KotlinLogging.logger {}

@Aspect
class TraceAspect {

    @Before("@annotation(com.kakaopaysec.aop.exam.annotation.Trace)")
    fun doTrace(joinPoint: JoinPoint) {
        val args = joinPoint.args
        logger.info { "[trace] {${joinPoint.signature}}, args = ${Arrays.toString(args)}" }
    }
}
