package com.kakaopaysec.aop.order.aop

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

private val logger = KotlinLogging.logger {}

@Aspect
class AspectV3 {

    // 반환 타입은 void여야 합니다.
    // 코드 내용은 비워둡니다.
    // 메서드 이름과 파라미터를 합쳐서 포인트컷 시그니처라고 합니다.
    @Pointcut("execution(* com.kakaopaysec.aop.order..*(..))")
    fun allOrder() {} // pointcut signature

    @Pointcut("execution(* *..*Service.*(..))")
    private fun allService() {} // pointcut signature


    @Around("allOrder()")
    fun doLog(joinPoint: ProceedingJoinPoint): Any? {
        logger.info { "[log] {${joinPoint.signature}}" }
        return joinPoint.proceed()
    }

    // com.kakaopaysec.aop.order 패키지 하위이면서 클래스 이름 패턴이 *Service인 경우에만 advice 적용
    @Around("allOrder() && allService()")
    fun doTransaction(joinPoint: ProceedingJoinPoint): Any? {
        try {
            logger.info { "[트랜잭션 시작] {${joinPoint.signature}}" }
            val result = joinPoint.proceed()
            logger.info { "[트랜잭션 커밋] {${joinPoint.signature}}" }

            return result
        } catch (e: Exception) {
            logger.info { "[트랜잭션 롤백] {${joinPoint.signature}}" }
            throw e
        } finally {
            logger.info { "[리소스 release] {${joinPoint.signature}}" }
        }
    }
}
