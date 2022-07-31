package com.kakaopaysec.aop.pointcut

import com.kakaopaysec.aop.member.service.MemberServiceImpl
import mu.KotlinLogging
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import java.lang.reflect.Method
import org.assertj.core.api.Assertions.assertThat


private val logger = KotlinLogging.logger {}

class ArgsTest {

    lateinit var helloMethod: Method

    @BeforeEach
    fun init() {
        helloMethod = MemberServiceImpl::class.java.getMethod("hello", String::class.java)
    }

    private fun pointcut(expression: String): AspectJExpressionPointcut {
        val pointcut = AspectJExpressionPointcut()
        pointcut.expression = expression
        return pointcut
    }

    @Test
    fun args() {
        assertThat(pointcut("args(String)")
            .matches(helloMethod, MemberServiceImpl::class.java)).isTrue
        assertThat(pointcut("args(Object)")
                .matches(helloMethod, MemberServiceImpl::class.java)).isTrue
        assertThat(pointcut("args()")
                .matches(helloMethod, MemberServiceImpl::class.java)).isFalse
        assertThat(pointcut("args(..)")
                .matches(helloMethod, MemberServiceImpl::class.java)).isTrue
        assertThat(pointcut("args(*)")
                .matches(helloMethod, MemberServiceImpl::class.java)).isTrue
        assertThat(pointcut("args(String,..)")
                .matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    fun argsVsExecution() {
        //Args - 상위 타입 적용 가능(런타임 시 객체 인스턴스로 판단함)
        assertThat(pointcut("args(String)")
            .matches(helloMethod, MemberServiceImpl::class.java)).isTrue
        assertThat(pointcut("args(java.io.Serializable)")
            .matches(helloMethod, MemberServiceImpl::class.java)).isTrue
        assertThat(pointcut("args(Object)")
            .matches(helloMethod, MemberServiceImpl::class.java)).isTrue

        // Execution - 파라미터 타입이 정확해야 됨(정적인 정보로 판단)
        assertThat(pointcut("execution(* *(String))")
            .matches(helloMethod, MemberServiceImpl::class.java)).isTrue

        assertThat(pointcut("execution(* *(java.io.Serializable))")
            .matches(helloMethod, MemberServiceImpl::class.java)).isFalse //매칭 실패 .matches(helloMethod, MemberServiceImpl.class)).isFalse();

        assertThat(pointcut("execution(* *(Object))")
            .matches(helloMethod, MemberServiceImpl::class.java)).isFalse //매칭 실패 .matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
}
