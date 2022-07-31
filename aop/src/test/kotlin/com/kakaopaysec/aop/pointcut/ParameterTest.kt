package com.kakaopaysec.aop.pointcut

import com.kakaopaysec.aop.member.annotation.ClassAop
import com.kakaopaysec.aop.member.annotation.MethodAop
import com.kakaopaysec.aop.member.service.MemberService
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

private val logger = KotlinLogging.logger {}

@Import(ParameterTest.ParameterAspect::class)
@SpringBootTest
class ParameterTest {

    @Autowired lateinit var memberService: MemberService


    @Test
    fun success() {
       logger.info { "memberService Proxy = {${memberService.javaClass}}" }
        memberService.hello("helloA")
    }

    @Aspect
    class ParameterAspect {

        @Pointcut("execution(* com.kakaopaysec.aop.member..*.*(..))")
        fun allMember() {}

        @Around("allMember()")
        fun logArgs1(joinPoint: ProceedingJoinPoint): Any? {
            val arg1 = joinPoint.args[0]
            logger.info { "[logArgs1] = {${joinPoint.signature}}, arg = {$arg1}" }
            return joinPoint.proceed()
        }

        @Around("allMember() && args(arg, ..)")
        fun logArgs2(joinPoint: ProceedingJoinPoint, arg: Any?): Any? {
            val arg1 = joinPoint.args[0]
            logger.info { "[logArgs2] = {${joinPoint.signature}}, arg = [$arg]" }
            return joinPoint.proceed()
        }

        @Before("allMember() && args(arg, ..)")
        fun logArgs3(arg: String) {
            logger.info { "[logArgs3] arg = {$arg}" }
        }

        @Before("allMember() && this(obj)")
        fun thisArgs(joinPoint: JoinPoint, obj: MemberService) {
            logger.info { "[this] = ${joinPoint.signature} , obj = {${obj.javaClass}}" }
        }

        @Before("allMember() && target(obj)")
        fun targetArgs(joinPoint: JoinPoint, obj: MemberService) {
            logger.info { "[target] = ${joinPoint.signature} , obj = {${obj.javaClass}}" }
        }

        @Before("allMember() && @target(annotation)")
        fun atTarget(joinPoint: JoinPoint, annotation: ClassAop) {
            logger.info { "[@target] = ${joinPoint.signature} , obj = {${annotation}}" }
        }

        @Before("allMember() && @within(annotation)")
        fun atWithin(joinPoint: JoinPoint, annotation: ClassAop) {
            logger.info { "[@within] = ${joinPoint.signature} , obj = {${annotation}}" }
        }

        @Before("allMember() && @annotation(annotation)")
        fun atAnnotation(joinPoint: JoinPoint, annotation: MethodAop) {
            logger.info { "[@annotation] = ${joinPoint.signature} , annotationValue = {${annotation.value}}" }
        }
    }
}
