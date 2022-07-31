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

@Import(ThisTargetTest.ThisTargetAspect::class)
@SpringBootTest(properties = ["spring.aop.proxy-target-class=true"]) // JDK 동적 프록시로 생
class ThisTargetTest {

    @Autowired lateinit var memberService: MemberService


    @Test
    fun success() {
        logger.info { "memberService Proxy = ${memberService.javaClass}" }
        memberService.hello("helloA")
    }

    @Aspect
    class ThisTargetAspect {

        @Around("this(com.kakaopaysec.aop.member.service.MemberService)")
        fun doThisInterface(joinPoint: ProceedingJoinPoint): Any? {
            logger.info { "[this-interface] {${joinPoint.signature}}" }
            return joinPoint.proceed()
        }

        @Around("target(com.kakaopaysec.aop.member.service.MemberService)")
        fun doTargetInterface(joinPoint: ProceedingJoinPoint): Any? {
            logger.info { "[target-interface] {${joinPoint.signature}}" }
            return joinPoint.proceed()
        }

        @Around("this(com.kakaopaysec.aop.member.service.MemberServiceImpl)")
        fun doThis(joinPoint: ProceedingJoinPoint): Any? {
            logger.info { "[this-implement] {${joinPoint.signature}}" }
            return joinPoint.proceed()
        }

        @Around("target(com.kakaopaysec.aop.member.service.MemberServiceImpl)")
        fun doTarget(joinPoint: ProceedingJoinPoint): Any? {
            logger.info { "[target-implement] {${joinPoint.signature}}" }
            return joinPoint.proceed()
        }
    }
}
