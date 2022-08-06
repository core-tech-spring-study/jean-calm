package com.kakaopaysec.core.beandefinition

import com.kakaopaysec.core.AppConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext

internal class BeanDefinitionTest {


    lateinit var ac: AnnotationConfigApplicationContext

    @BeforeEach
    fun setUp() {
        ac = AnnotationConfigApplicationContext(AppConfig::class.java)
    }

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    fun findApplicationBean() {
        val beanDefinitionNames = ac.beanDefinitionNames

        beanDefinitionNames.forEach {
            val beanDefinition = ac.getBeanDefinition(it)
            println("beanDefinitionName = $beanDefinition, beanDefinition = $beanDefinition")
        }
    }
}
