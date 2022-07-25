package com.kakaopaysec.proxy.config

import com.kakaopaysec.proxy.app.v2.api.OrderApiV2
import com.kakaopaysec.proxy.app.v2.repository.OrderRepositoryV2
import com.kakaopaysec.proxy.app.v2.service.OrderServiceV2
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppV2Config {

    @Bean
    fun orderApiV2(): OrderApiV2 {
        return OrderApiV2(orderServiceV2())
    }

    @Bean
    fun orderServiceV2(): OrderServiceV2 {
        return OrderServiceV2(orderRepositoryV2())
    }

    @Bean
    fun orderRepositoryV2(): OrderRepositoryV2 {
        return OrderRepositoryV2()
    }
}
