package com.kakaopaysec.aop.pointcut

import com.kakaopaysec.aop.member.annotation.ClassAop
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

private val logger = KotlinLogging.logger {}

@Import(AtTargetWithinTest.Config::class)
@SpringBootTest
class AtTargetWithinTest {

    @Autowired lateinit var child: Child

    @Test
    fun success() {
        logger.info { "child Proxy = {${child.javaClass}}" }
        child.childMethod()
        child.parentMethod() // 부모 클래스만 있는 메서드
    }


     open class Config {

        @Bean
        fun parent(): Parent {
            return Parent()
        }

        @Bean
        fun child(): Child {
            return Child()
        }

        @Bean
        fun atTargetAtWithinAspect(): AtTargetAtWithinAspect {
            return AtTargetAtWithinAspect()
        }
    }

    open class Parent {
        open fun parentMethod() {}
    }

    @ClassAop
    open class Child: Parent() {
        open fun childMethod() {}
    }

    @Aspect
    class AtTargetAtWithinAspect {

        // @target: 인스턴스 기준으로 모든 메서드의 조인 포인트를 선정, 부모 타입의 메서드도 적용
        @Around("execution(* com.kakaopaysec.aop..*(..)) && " +
                "@target(com.kakaopaysec.aop.member.annotation.ClassAop)")
        fun atTarget(joinPoint: ProceedingJoinPoint): Any? {
            logger.info { "[@target] {${joinPoint.signature}}" }
            return joinPoint.proceed()
        }

        // @within: 선택된 클래스 내부에 있는 메서드만 조인 포인트로 선정, 부모 타입의 메서드는 적용되지 않음
        @Around("execution(* com.kakaopaysec.aop..*(..)) && " +
                "@within(com.kakaopaysec.aop.member.annotation.ClassAop)")
        fun atWithin(joinPoint: ProceedingJoinPoint): Any? {
            logger.info { "[@within] {${joinPoint.signature}}" }
            return joinPoint.proceed()
        }
    }
}
