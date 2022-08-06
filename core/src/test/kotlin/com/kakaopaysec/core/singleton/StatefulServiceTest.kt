package com.kakaopaysec.core.singleton

import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean

internal class StatefulServiceTest {

    @Test
    fun test() {
        val ac = AnnotationConfigApplicationContext(TestConfig::class.java)
        val statefulService1 = ac.getBean(StatefulService::class.java)
        val statefulService2 = ac.getBean(StatefulService::class.java)

        // ThreadA
        val userAPrice = statefulService1.order("userA", 10000)

        // ThreadB
        val userBPrice = statefulService1.order("userB", 20000)

        println("price = $userAPrice")

    }

    class TestConfig {

        @Bean
        fun statefulService(): StatefulService {
            return StatefulService()
        }
    }
}
