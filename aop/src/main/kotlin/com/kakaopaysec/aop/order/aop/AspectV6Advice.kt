package com.kakaopaysec.aop.order.aop

import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

private val logger = KotlinLogging.logger {}

@Aspect
class AspectV6Advice {

    // 반환 타입은 void여야 합니다.
    // 코드 내용은 비워둡니다.
    // 메서드 이름과 파라미터를 합쳐서 포인트컷 시그니처라고 합니다.


    @Around("com.kakaopaysec.aop.order.aop.Pointcuts.allOrder()")
    fun doLog(joinPoint: ProceedingJoinPoint): Any? {
        logger.info { "[log] {${joinPoint.signature}}" }
        return joinPoint.proceed()
    }

    // com.kakaopaysec.aop.order 패키지 하위이면서 클래스 이름 패턴이 *Service인 경우에만 advice 적용
    // @Around가 적용된 메서드는 파라미터로 무조건 ProceedingJoinPoint로 선언해야합니다. 그 이유는 다음 어드바이스나 타겟을 호출해야하기 때문에...
    // 만약 ProceedingJoinPoint를 선언하지 않으면 실제로 타겟이 호출이 안되기 때문에 위험합니다.
    // 참고로 around는 타겟의 return 타입을 조작할 수 있습니다.
    @Around("com.kakaopaysec.aop.order.aop.Pointcuts.orderAndService()")
    fun doTransaction(joinPoint: ProceedingJoinPoint): Any? {
        try {
            // @Before
            logger.info { "[트랜잭션 시작] {${joinPoint.signature}}" }
            val result = joinPoint.proceed()

            // @AfterReturning
            logger.info { "[트랜잭션 커밋] {${joinPoint.signature}}" }

            return result
        } catch (e: Exception) {
            // @AfterThrowing
            logger.info { "[트랜잭션 롤백] {${joinPoint.signature}}" }
            throw e
        } finally {
            // @After
            logger.info { "[리소스 release] {${joinPoint.signature}}" }
        }
    }

    @Before("com.kakaopaysec.aop.order.aop.Pointcuts.orderAndService()")
    fun doBefore(joinPoint: JoinPoint) {
        logger.info { "[before] {${joinPoint.signature}}" }
    }

    // returning 옵션은 실제 타겟의 리턴타입에 매칭되는 타입만 적용됩니다. 만약 void인데 advice의 result type이 String인 경우 어드바이스가 실행되지 않음
    @AfterReturning(value = "com.kakaopaysec.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    fun doReturn(joinPoint: JoinPoint, result: Any?) {
        logger.info { "[return] {${joinPoint.signature}}" }
    }

    @AfterThrowing(value = "com.kakaopaysec.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    fun doThrowing(joinPoint: JoinPoint, ex: Exception) {
        logger.info { "[ex] {${ex.message}}" }
    }

    @After(value = "com.kakaopaysec.aop.order.aop.Pointcuts.orderAndService()")
    fun doAfter(joinPoint: JoinPoint) {
        logger.info { "[after] {${joinPoint.signature}}" }
    }
}
