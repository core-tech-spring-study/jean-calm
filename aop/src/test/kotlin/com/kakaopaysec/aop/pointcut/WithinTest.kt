package com.kakaopaysec.aop.pointcut

import com.kakaopaysec.aop.member.service.MemberServiceImpl
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import java.lang.reflect.Method

private val logger = KotlinLogging.logger {}

class WithinTest {

    val pointcut = AspectJExpressionPointcut()
    lateinit var helloMethod: Method

    @BeforeEach
    fun init() {
        helloMethod = MemberServiceImpl::class.java.getMethod("hello", String::class.java)
    }

    /**
     * within은 표현식에 부모타입을 지정하면 안됨, 이점이 execution과의 차이점입니다.
     */

    @Test
    fun withinExact() {
        // given
        pointcut.expression = "within(com.kakaopaysec.aop.member.service.MemberServiceImpl)"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    fun withinStar() {
        // given
        pointcut.expression = "within(com.kakaopaysec.aop.member.service.*Service*)"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    fun withinSubPackage() {
        // given
        pointcut.expression = "within(com.kakaopaysec.aop..*)"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    @DisplayName("타겟의 타입에만 직접 적용, 인터페이스를 선정하면 안됩니다.")
    fun withinSuperTypeFalse() {
        // given
        pointcut.expression = "within(com.kakaopaysec.aop.member.service.MemberService)"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isFalse
    }

    @Test
    @DisplayName("execution은 타입기반, 인터페이스 선정 가능")
    fun executionSuperTypeTrue() {
        // given
        pointcut.expression = "execution(* com.kakaopaysec.aop.member.service.MemberService.*(..))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }
}
