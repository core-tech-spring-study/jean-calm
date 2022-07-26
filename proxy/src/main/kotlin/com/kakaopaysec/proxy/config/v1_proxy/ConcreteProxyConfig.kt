package com.kakaopaysec.proxy.config.v1_proxy

import com.kakaopaysec.proxy.app.v2.api.OrderApiV2
import com.kakaopaysec.proxy.app.v2.repository.OrderRepositoryV2
import com.kakaopaysec.proxy.app.v2.service.OrderServiceV2
import com.kakaopaysec.proxy.config.v1_proxy.concrete_proxy.api.OrderApiConcreteProxy
import com.kakaopaysec.proxy.config.v1_proxy.concrete_proxy.repository.OrderRepositoryConcreteProxy
import com.kakaopaysec.proxy.config.v1_proxy.concrete_proxy.service.OrderServiceConcreteProxy
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConcreteProxyConfig {

    @Bean
    fun orderApiV2(trace: LogTrace): OrderApiV2 {
        val orderApiV2 = OrderApiV2(orderServiceV2(trace))
        return OrderApiConcreteProxy(orderApiV2, trace)
    }

    @Bean
    fun orderServiceV2(trace: LogTrace): OrderServiceV2 {
        val orderServiceV2 = OrderServiceV2(orderRepositoryV2(trace))
        return OrderServiceConcreteProxy(orderServiceV2, trace)
    }

    @Bean
    fun orderRepositoryV2(trace: LogTrace): OrderRepositoryV2 {
        val orderRepositoryV2 = OrderRepositoryV2()
        return OrderRepositoryConcreteProxy(orderRepositoryV2, trace)
    }
}
