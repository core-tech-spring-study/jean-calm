package com.kakaopaysec.aop

import com.kakaopaysec.aop.member.service.MemberService
import com.kakaopaysec.aop.member.service.MemberServiceImpl
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.aop.framework.ProxyFactory

private val logger = KotlinLogging.logger {}

class ProxyCastingTest {

    @Test
    fun jdkProxy() {

        val target = MemberServiceImpl()
        val proxyFactory = ProxyFactory(target)
        proxyFactory.isProxyTargetClass = false // JDK 동적 프록시

        // 프록시를 인터페이스로 캐스팅 성공
        val memberServiceProxy = proxyFactory.proxy as MemberService

        // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발
        assertThrows(ClassCastException::class.java) {
            val castingMemberService = memberServiceProxy as MemberServiceImpl
        }
    }

    @Test
    fun cglibProxy() {

        val target = MemberServiceImpl()
        val proxyFactory = ProxyFactory(target)
        proxyFactory.isProxyTargetClass = true // JDK 동적 프록시

        // 프록시를 인터페이스로 캐스팅 성공
        val memberServiceProxy = proxyFactory.proxy as MemberService

        logger.info { "proxy class = ${memberServiceProxy.javaClass}" }

        // CGLIB로 구현체로 캐스팅 성공
        val castingMemberService = memberServiceProxy as MemberServiceImpl
    }
}
