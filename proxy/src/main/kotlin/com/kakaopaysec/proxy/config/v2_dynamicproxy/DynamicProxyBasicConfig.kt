package com.kakaopaysec.proxy.config.v2_dynamicproxy

import com.kakaopaysec.proxy.app.v1.api.OrderApiV1
import com.kakaopaysec.proxy.app.v1.api.OrderApiV1Impl
import com.kakaopaysec.proxy.app.v1.repository.OrderRepositoryV1
import com.kakaopaysec.proxy.app.v1.repository.OrderRepositoryV1Impl
import com.kakaopaysec.proxy.app.v1.service.OrderServiceV1
import com.kakaopaysec.proxy.app.v1.service.OrderServiceV1Impl
import com.kakaopaysec.proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandler
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.reflect.Proxy

@Configuration
class DynamicProxyBasicConfig {

    @Bean
    fun orderApiV1(trace: LogTrace): OrderApiV1 {

        val orderApiV1 = OrderApiV1Impl(orderServiceV1(trace))
        val proxy = Proxy.newProxyInstance(
            OrderApiV1::class.java.classLoader,
            arrayOf(OrderApiV1::class.java),
            LogTraceBasicHandler(orderApiV1, trace)
        )

        return proxy as OrderApiV1
    }

    @Bean
    fun orderServiceV1(trace: LogTrace): OrderServiceV1 {
        val orderServiceV1 = OrderServiceV1Impl(orderRepositoryV1(trace))

        val proxy = Proxy.newProxyInstance(
            OrderServiceV1::class.java.classLoader,
            arrayOf(OrderServiceV1::class.java),
            LogTraceBasicHandler(orderServiceV1, trace)
        )

        return proxy as OrderServiceV1
    }


    @Bean
    fun orderRepositoryV1(trace: LogTrace): OrderRepositoryV1 {
        val orderRepositoryV1 = OrderRepositoryV1Impl()

        val proxy = Proxy.newProxyInstance(
            OrderRepositoryV1::class.java.classLoader,
            arrayOf(OrderRepositoryV1::class.java),
            LogTraceBasicHandler(orderRepositoryV1, trace)
        )

        return proxy as OrderRepositoryV1
    }
}
