package com.kakaopaysec.aop.exam.aop

import com.kakaopaysec.aop.exam.annotation.Retry
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

private val logger = KotlinLogging.logger {}

@Aspect
class RetryAspect {

    // 파라미터로 받은 타입정보를 추출해서 어노테이션 기반 pointcut 지정을 간소화할 수 있습니다.
    @Around("@annotation(retry)")
    fun doRetry(joinPoint: ProceedingJoinPoint, retry: Retry): Any? {
        logger.info { "[retry] {${joinPoint.signature}}, retry = $retry" }
        val maxRetry = retry.value
        logger.info { "maxRetry: $maxRetry" }

        var exceptionHolder: Exception? = null

        for (retryCount in 1 .. maxRetry) {
            try {
                logger.info { "[retry] try count = {$retryCount} / {$maxRetry}" }
                return joinPoint.proceed()
            } catch (e: Exception) {
                exceptionHolder = e
            }
        }

        throw exceptionHolder!!
    }
}
