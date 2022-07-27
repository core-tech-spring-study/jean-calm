package com.kakaopaysec.proxy.proxyfactory

import com.kakaopaysec.proxy.pureproxy.proxy.common.advice.TimeAdvice
import com.kakaopaysec.proxy.pureproxy.proxy.common.service.ConcreteService
import com.kakaopaysec.proxy.pureproxy.proxy.common.service.ServiceImpl
import com.kakaopaysec.proxy.pureproxy.proxy.common.service.ServiceInterface
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.aop.framework.ProxyFactory
import org.springframework.aop.support.AopUtils

private val logger = KotlinLogging.logger {}

class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    fun interfaceProxy() {
        // given
        val target = ServiceImpl()
        val proxyFactory = ProxyFactory(target)

        proxyFactory.addAdvice(TimeAdvice())
        val proxy = proxyFactory.proxy as ServiceInterface

        // when
        logger.info { "targetClass = ${target.javaClass}" }
        logger.info { "proxyClass = ${proxy.javaClass}" }

        proxy.save()
        proxy.find()

        // then
        assertThat(AopUtils.isAopProxy(proxy)).isTrue
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse
    }

    @Test
    @DisplayName("구체 클레스가 있으면 CGLIB 프록시 사용")
    fun concreteProxy() {
        // given
        val target = ConcreteService()
        val proxyFactory = ProxyFactory(target)

        proxyFactory.addAdvice(TimeAdvice())
        val proxy = proxyFactory.proxy as ConcreteService

        // when
        logger.info { "targetClass = ${target.javaClass}" }
        logger.info { "proxyClass = ${proxy.javaClass}" }

        proxy.call()

        // then
        assertThat(AopUtils.isAopProxy(proxy)).isTrue
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue
    }

    @Test
    @DisplayName("proxyTagetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB 프록시 사용하고, 클래스 기반 프록시 사용")
    fun proxyTargetClass() {
        // given
        val target = ServiceImpl()
        val proxyFactory = ProxyFactory(target)
        proxyFactory.isProxyTargetClass = true
        proxyFactory.addAdvice(TimeAdvice())
        val proxy = proxyFactory.proxy as ServiceInterface

        // when
        logger.info { "targetClass = ${target.javaClass}" }
        logger.info { "proxyClass = ${proxy.javaClass}" }

        proxy.save()
        proxy.find()

        // then
        assertThat(AopUtils.isAopProxy(proxy)).isTrue
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue
    }
}
