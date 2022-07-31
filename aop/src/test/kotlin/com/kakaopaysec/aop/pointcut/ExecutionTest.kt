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

class ExecutionTest {

    val pointcut = AspectJExpressionPointcut()
    lateinit var helloMethod: Method

    @BeforeEach
    fun init() {
        helloMethod = MemberServiceImpl::class.java.getMethod("hello", String::class.java)
    }

    @Test
    fun invokeMethod() {
        // given
        logger.info { "method: $helloMethod" }

        val target = MemberServiceImpl()
        val name = "OK"

        // when
        val result = helloMethod.invoke(target, name)

        logger.info { "result: $result" }

        // then
        assertThat(result).isEqualTo(name)
    }

    @Test
    fun exactMatch() {
        // given
        pointcut.expression = "execution(public String com.kakaopaysec.aop.member.service.MemberServiceImpl.hello(String))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    @DisplayName("pointcut 모든 메서드 match 예제")
    fun allMatch() {
        // given
        pointcut.expression = "execution(* *(..))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    @DisplayName("pointcut 메서드 naming match 예제")
    fun nameMatch() {
        // given
        pointcut.expression = "execution(* hello(..))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    @DisplayName("pointcut pattern match 예제")
    fun nameMatchStar1() {
        // given
        pointcut.expression = "execution(* hel*(..))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    @DisplayName("pointcut pattern no matching 예제")
    fun nameMatchFalse() {
        // given
        pointcut.expression = "execution(* nono(..))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isFalse
    }

    @Test
    @DisplayName("pointcut 패키지 matching 예제")
    fun packageExactMatch1() {
        // given
        pointcut.expression = "execution(* com.kakaopaysec.aop.member.service.MemberServiceImpl.hello(..))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    @DisplayName("pointcut 패키지 하위 클래스 alias matching 예제")
    fun packageExactMatch2() {
        // given
        pointcut.expression = "execution(* com.kakaopaysec.aop.member.service.*.*(..))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    @DisplayName("pointcut 패키지 matching 실패 예제")
    fun packageExactFalse() {
        // given
        pointcut.expression = "execution(* com.kakaopaysec.aop.member.*.*(..))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isFalse
    }

    @Test
    @DisplayName("pointcut 하위 패키지 포함 matching  예제")
    fun packageExactMatch3() {
        // given
        pointcut.expression = "execution(* com.kakaopaysec.aop..*.*(..))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    @DisplayName("pointcut type matching  예제")
    fun typeExactMatch() {
        // given
        pointcut.expression = "execution(* com.kakaopaysec.aop.member.service.MemberServiceImpl.*(..))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    @DisplayName("pointcut super type matching  예제")
    fun typeMatchSuperType() {
        // given
        pointcut.expression = "execution(* com.kakaopaysec.aop.member.service.MemberService.*(..))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    // 인터페이스에 선언한 메서드만 pointcut matching이 되는 것을 유의하자.
    @Test
    @DisplayName("pointcut super type internal 메서드 matching  예제")
    fun typeMatchInternal() {
        // given
        pointcut.expression = "execution(* com.kakaopaysec.aop.member.service.MemberService.*(..))"

        val internalMethod = MemberServiceImpl::class.java.getMethod("internal", String::class.java)

        // then
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl::class.java)).isFalse
    }

    @Test
    @DisplayName("pointcut arguments matching  예제")
    fun argMatch() {
        // given
        pointcut.expression = "execution(* *(String))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    @DisplayName("pointcut no arguments matching  예제")
    fun argMatchNoArgs() {
        // given
        pointcut.expression = "execution(* *())"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isFalse
    }

    @Test
    @DisplayName("pointcut 하나의 파라미터 허용, 모든 타입 허용  예제")
    fun argMatchStar() {
        // given
        pointcut.expression = "execution(* *(*))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    @DisplayName("pointcut 모든 파라미터 허용, 모든 타입 허용 예제")
    fun argMatchAll() {
        // given
        pointcut.expression = "execution(* *(..))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }

    @Test
    @DisplayName("pointcut String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용 예제")
    fun argMatchComplex() {
        // given
        pointcut.expression = "execution(* *(String, ..))"

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue
    }
}
