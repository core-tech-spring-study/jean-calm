package com.kakaopaysec.proxy.cglib

import com.kakaopaysec.proxy.cglib.code.TimeMethodInterceptor
import com.kakaopaysec.proxy.pureproxy.proxy.common.service.ConcreteService
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.cglib.proxy.Enhancer

private val logger = KotlinLogging.logger {}

class CglibTest {

    @Test
    fun cglib() {
        val target = ConcreteService()

        val enhancer = Enhancer()
        enhancer.setSuperclass(ConcreteService::class.java)
        enhancer.setCallback(TimeMethodInterceptor(target))
        val proxy = enhancer.create() as ConcreteService

        logger.info { "targetClass = ${target.javaClass}" }
        logger.info { "proxyClass = ${proxy.javaClass}" }

        proxy.call()
    }
}
