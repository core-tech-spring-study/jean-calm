package com.kakaopaysec.proxy.config

import com.kakaopaysec.proxy.app.v1.api.OrderApiV1
import com.kakaopaysec.proxy.app.v1.api.OrderApiV1Impl
import com.kakaopaysec.proxy.app.v1.repository.OrderRepositoryV1
import com.kakaopaysec.proxy.app.v1.repository.OrderRepositoryV1Impl
import com.kakaopaysec.proxy.app.v1.service.OrderServiceV1
import com.kakaopaysec.proxy.app.v1.service.OrderServiceV1Impl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppV1Config {

    @Bean
    fun orderApiV1(): OrderApiV1 {
        return OrderApiV1Impl(orderServiceV1())
    }

    @Bean
    fun orderServiceV1(): OrderServiceV1 {
        return OrderServiceV1Impl(orderRepositoryV1())
    }

    @Bean
    fun orderRepositoryV1(): OrderRepositoryV1 {
        return OrderRepositoryV1Impl()
    }
}
