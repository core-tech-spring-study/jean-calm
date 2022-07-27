package com.kakaopaysec.proxy.pureproxy.proxy.jdkdynamic

import com.kakaopaysec.proxy.common.logger
import com.kakaopaysec.proxy.pureproxy.proxy.jdkdynamic.code.*
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import java.lang.reflect.Proxy

private val logger = KotlinLogging.logger {}

class JdkDynamicProxyTest {

    @Test
    fun dynamicA() {

        val target = AImpl()
        val handler = TimeInvocationHandler(target)

        val aInterface =  Proxy.newProxyInstance(AInterface::class.java.classLoader,
            arrayOf(AInterface::class.java), handler) as AInterface

        logger.info { "result = ${aInterface.call()}" }
    }

    @Test
    fun dynamicB() {
        val target = BImpl()
        val handler = TimeInvocationHandler(target)

        val bInterface =  Proxy.newProxyInstance(BInterface::class.java.classLoader,
            arrayOf(BInterface::class.java), handler) as BInterface

        logger.info { "result = ${bInterface.call()}" }
    }
}
