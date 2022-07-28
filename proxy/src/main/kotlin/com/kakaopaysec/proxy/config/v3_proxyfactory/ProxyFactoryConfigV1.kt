package com.kakaopaysec.proxy.config.v3_proxyfactory

import com.kakaopaysec.proxy.app.v1.api.OrderApiV1
import com.kakaopaysec.proxy.app.v1.api.OrderApiV1Impl
import com.kakaopaysec.proxy.app.v1.repository.OrderRepositoryV1
import com.kakaopaysec.proxy.app.v1.repository.OrderRepositoryV1Impl
import com.kakaopaysec.proxy.app.v1.service.OrderServiceV1
import com.kakaopaysec.proxy.app.v1.service.OrderServiceV1Impl
import com.kakaopaysec.proxy.config.v3_proxyfactory.advice.LogTraceAdvice
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import mu.KotlinLogging
import org.springframework.aop.Advisor
import org.springframework.aop.framework.ProxyFactory
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.NameMatchMethodPointcut
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val logger = KotlinLogging.logger {}

@Configuration
class ProxyFactoryConfigV1 {

    @Bean
    fun orderApiV1(trace: LogTrace): OrderApiV1 {
        val target = OrderApiV1Impl(orderServiceV1(trace))
        val proxyFactory = ProxyFactory(target)

        proxyFactory.addAdvisor(getAdvisor(trace))

        val proxy = proxyFactory.proxy as OrderApiV1

        logger.info { "ProxyFactory proxy = ${proxy.javaClass}, target = $target" }

        return proxy
    }

    @Bean
    fun orderServiceV1(trace: LogTrace): OrderServiceV1 {
        val target = OrderServiceV1Impl(orderRepositoryV1(trace))
        val proxyFactory = ProxyFactory(target)

        proxyFactory.addAdvisor(getAdvisor(trace))

        val proxy = proxyFactory.proxy as OrderServiceV1

        logger.info { "ProxyFactory proxy = ${proxy.javaClass}, target = $target" }

        return proxy
    }

    @Bean
    fun orderRepositoryV1(trace: LogTrace): OrderRepositoryV1 {
        val target = OrderRepositoryV1Impl()
        val proxyFactory = ProxyFactory(target)

        proxyFactory.addAdvisor(getAdvisor(trace))

        val proxy = proxyFactory.proxy as OrderRepositoryV1

        logger.info { "ProxyFactory proxy = ${proxy.javaClass}, target = $target" }

        return proxy
    }

    private fun getAdvisor(trace: LogTrace): Advisor {
        // pointcut
        val pointcut = NameMatchMethodPointcut()
        pointcut.setMappedNames("request*", "order*", "save**")

        // advice
        val advise = LogTraceAdvice(trace)

        return DefaultPointcutAdvisor(pointcut, advise)
    }
}
