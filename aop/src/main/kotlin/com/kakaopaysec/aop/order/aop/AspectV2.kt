package com.kakaopaysec.aop.order.aop

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

private val logger = KotlinLogging.logger {}

@Aspect
class AspectV2 {

    // 반환 타입은 void여야 합니다.
    // 코드 내용은 비워둡니다.
    // 메서드 이름과 파라미터를 합쳐서 포인트컷 시그니처라고 합니다.
    @Pointcut("execution(* com.kakaopaysec.aop.order..*(..))")
    fun allOrder() {} // pointcut signature


    @Around("allOrder()")
    fun doLog(joinPoint: ProceedingJoinPoint): Any? {
        logger.info { "[log] {${joinPoint.signature}}" }
        return joinPoint.proceed()
    }
}
