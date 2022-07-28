package com.kakaopaysec.proxy.config.v3_proxyfactory


import com.kakaopaysec.proxy.app.v2.api.OrderApiV2
import com.kakaopaysec.proxy.app.v2.repository.OrderRepositoryV2
import com.kakaopaysec.proxy.app.v2.service.OrderServiceV2
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
class ProxyFactoryConfigV2 {

    @Bean
    fun orderApiV2(trace: LogTrace): OrderApiV2 {
        val target = OrderApiV2(orderServiceV2(trace))
        val proxyFactory = ProxyFactory(target)

        proxyFactory.addAdvisor(getAdvisor(trace))

        val proxy = proxyFactory.proxy as OrderApiV2

        logger.info { "ProxyFactory proxy = ${proxy.javaClass}, target = $target" }

        return proxy
    }

    @Bean
    fun orderServiceV2(trace: LogTrace): OrderServiceV2 {
        val target = OrderServiceV2(orderRepositoryV2(trace))
        val proxyFactory = ProxyFactory(target)

        proxyFactory.addAdvisor(getAdvisor(trace))

        val proxy = proxyFactory.proxy as OrderServiceV2

        logger.info { "ProxyFactory proxy = ${proxy.javaClass}, target = $target" }

        return proxy
    }

    @Bean
    fun orderRepositoryV2(trace: LogTrace): OrderRepositoryV2 {
        val target = OrderRepositoryV2()
        val proxyFactory = ProxyFactory(target)

        proxyFactory.addAdvisor(getAdvisor(trace))

        val proxy = proxyFactory.proxy as OrderRepositoryV2

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
