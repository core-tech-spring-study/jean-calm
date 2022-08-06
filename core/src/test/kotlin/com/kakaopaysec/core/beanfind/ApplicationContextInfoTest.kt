package com.kakaopaysec.core.beanfind

import com.kakaopaysec.core.AppConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class ApplicationContextInfoTest {

    lateinit var ac: AnnotationConfigApplicationContext

    @BeforeEach
    fun setUp() {
        ac = AnnotationConfigApplicationContext(AppConfig::class.java)
    }

    @Test
    @DisplayName("모든 빈 출력하기")
    fun findAllBean() {
        // given
        val beanDefinitionNames = ac.beanDefinitionNames

        // when
        println("SIZE: ${beanDefinitionNames.size}")

        beanDefinitionNames.forEach {
            val bean = ac.getBean(it)
            println("name = $it, object = $bean")
        }
    }

    @Test
    @DisplayName("어플리케이션 빈 출력하기")
    fun findApplicationBean() {
        // given
        val beanDefinitionNames = ac.beanDefinitionNames

        // when
        println("SIZE: ${beanDefinitionNames.size}")

        beanDefinitionNames.forEach {
            val beanDefinition = ac.getBeanDefinition(it)

            if (beanDefinition.role == BeanDefinition.ROLE_APPLICATION) {
                val bean = ac.getBean(it)
                println("name = $it, object = $bean")
            }
        }
    }
}
