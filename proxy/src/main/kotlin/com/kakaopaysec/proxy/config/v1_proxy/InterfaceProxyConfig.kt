package com.kakaopaysec.proxy.config.v1_proxy

import com.kakaopaysec.proxy.app.v1.api.OrderApiV1
import com.kakaopaysec.proxy.app.v1.api.OrderApiV1Impl
import com.kakaopaysec.proxy.app.v1.repository.OrderRepositoryV1
import com.kakaopaysec.proxy.app.v1.repository.OrderRepositoryV1Impl
import com.kakaopaysec.proxy.app.v1.service.OrderServiceV1
import com.kakaopaysec.proxy.app.v1.service.OrderServiceV1Impl
import com.kakaopaysec.proxy.config.v1_proxy.interface_proxy.api.OrderApiInterfaceProxy
import com.kakaopaysec.proxy.config.v1_proxy.interface_proxy.repository.OrderRepositoryInterfaceProxy
import com.kakaopaysec.proxy.config.v1_proxy.interface_proxy.service.OrderServiceInterfaceProxy
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import com.kakaopaysec.proxy.log.trace.logtrace.ThreadLocalLogTrace
import org.apache.juli.logging.Log
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InterfaceProxyConfig {

    @Bean
    fun orderApiV1(trace: LogTrace): OrderApiV1 {
        val orderApiImpl = OrderApiV1Impl(orderServiceV1(trace))
        return OrderApiInterfaceProxy(orderApiImpl, trace)
    }

    @Bean
    fun orderServiceV1(trace: LogTrace): OrderServiceV1 {
        val orderServiceV1Impl = OrderServiceV1Impl(orderRepositoryV1(trace))
        return OrderServiceInterfaceProxy(orderServiceV1Impl, trace)
    }

    @Bean
    fun orderRepositoryV1(trace: LogTrace): OrderRepositoryV1 {
        val orderRepositoryV1Impl = OrderRepositoryV1Impl()
        return OrderRepositoryInterfaceProxy(orderRepositoryV1Impl, trace)
    }
}
