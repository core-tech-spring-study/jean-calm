package com.kakaopaysec.aop.pointcut

import com.kakaopaysec.aop.member.service.MemberService
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

private val logger = KotlinLogging.logger {}

@Import(AtAnnotationTest.AtAnnotationAspect::class)
@SpringBootTest
class AtAnnotationTest {

    @Autowired lateinit var memberService: MemberService

    @Test
    fun success() {
        logger.info { "memberService Proxy = {${memberService.javaClass}}" }
        memberService.hello("helloA")
    }

    @Aspect
    class AtAnnotationAspect {
        @Around("@annotation(com.kakaopaysec.aop.member.annotation.MethodAop)")
        fun doAtAnnotation(joinPoint: ProceedingJoinPoint): Any? {
            logger.info { "[@annotation] {${joinPoint.signature}}" }
            return joinPoint.proceed()
        }
    }

}
