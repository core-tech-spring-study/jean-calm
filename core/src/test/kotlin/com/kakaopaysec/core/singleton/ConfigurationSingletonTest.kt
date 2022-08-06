package com.kakaopaysec.core.singleton

import com.kakaopaysec.core.AppConfig
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext

internal class ConfigurationSingletonTest {

    @Test
    fun configurationDeep() {
        val ac = AnnotationConfigApplicationContext(AppConfig::class.java)
        val bean = ac.getBean(AppConfig::class.java)

        println("bean = ${bean.javaClass}")
    }
}
