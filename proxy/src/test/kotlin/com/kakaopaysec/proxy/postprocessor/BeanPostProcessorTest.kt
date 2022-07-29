package com.kakaopaysec.proxy.postprocessor

import mu.KotlinLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val logger = KotlinLogging.logger {}

class BeanPostProcessorTest {

    @Test
    fun basicConfig() {
        val applicationContext = AnnotationConfigApplicationContext(BeanPostProcessorConfig::class.java)

      /*  val helloA = applicationContext.getBean("beanA", A::class.java)
        helloA.helloA()
        }*/
        // beanA라는 이름으로 A 객체가 스프링 빈 저장소에 저장되야하지만, BeanPostProcessor가 제공해주는 default 메서드인
        // postProcessAfterInitialization()를 오버라이딩하여 객체를 A -> B로 변경적용함
        val helloB = applicationContext.getBean("beanA", B::class.java)
        helloB.helloB()

        Assertions.assertThrows(NoSuchBeanDefinitionException::class.java) {
            applicationContext.getBean(A::class.java)
        }
    }

    @Configuration
    class BeanPostProcessorConfig {

        @Bean(name = ["beanA"])
        fun a(): A {
            return A()
        }

        fun b(): B {
            return B()
        }

        @Bean
        fun helloPostProcessor(): AToBPostProcessor {
            return AToBPostProcessor()
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

    // 후킹 포인트
    class AToBPostProcessor: BeanPostProcessor {
        override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
            logger.info { "beanName = $beanName, bean = $bean" }

             return when (bean) {
                is A -> B()
                else -> bean
            }
        }
    }
}
