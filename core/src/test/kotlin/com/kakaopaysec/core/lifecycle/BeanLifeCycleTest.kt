package com.kakaopaysec.core.lifecycle

import org.junit.jupiter.api.Test
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

internal class BeanLifeCycleTest {

    @Test
    fun lifeCycleTest() {
        val ac: ConfigurableApplicationContext = AnnotationConfigApplicationContext(LifeCycleConfig::class.java)
        val client = ac.getBean(NetworkClient::class.java)
        ac.close()
    }

    @Configuration
    class LifeCycleConfig {

        @Bean
        fun networkClient(): NetworkClient {
            val networkClient = NetworkClient()
            networkClient.url = "https://kakaopaysec.spirng-boot.com"
            return networkClient
        }
    }
}
