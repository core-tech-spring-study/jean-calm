package com.kakaopaysec.proxy.postprocessor

import mu.KotlinLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val logger = KotlinLogging.logger {}

class BasicTest {

    @Test
    fun basicConfig() {
        val applicationContext = AnnotationConfigApplicationContext(BasicConfig::class.java)

        val helloA = applicationContext.getBean("beanA", A::class.java)
        helloA.helloA()

        Assertions.assertThrows(NoSuchBeanDefinitionException::class.java) {
            applicationContext.getBean(B::class.java)
        }
    }

    @Configuration
    class BasicConfig {

        @Bean(name = ["beanA"])
        fun a(): A {
            return A()
        }

        fun b(): B {
            return B()
        }
    }

    class A {
        fun helloA() {
            logger.info { "hello A" }
        }
    }

    class B{
        fun helloB() {
            logger.info { "hello B" }
        }
    }
}
