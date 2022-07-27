package com.kakaopaysec.proxy.pureproxy.proxy.jdkdynamic

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import java.lang.reflect.Method

private val logger = KotlinLogging.logger {}

class ReflectionTest {

    @Test
    fun reflection0() {
        val target = Hello()

        // 공통 로직1 시작
        logger.info { "Start" }
        val result1 = target.callA()
        logger.info { "result1 = $result1" }
        // 공통 로직1 종료

        // 공통 로직2 시작
        logger.info { "Start" }
        val result2 = target.callB()
        logger.info { "result2 = $result2" }
        // 공통 로직2 종료
    }

    @Test
    fun reflection1() {
        val classHello = Class.forName("com.kakaopaysec.proxy.pureproxy.proxy.jdkdynamic.Hello")

        val target = Hello()

        val methodA = classHello.getMethod("callA")
        val result1 = methodA.invoke(target)
        logger.info { "result1 = $result1" }

        val methodB = classHello.getMethod("callB")
        val result2 = methodB.invoke(target)
        logger.info { "result2 = $result2" }

    }

    @Test
    fun reflection2() {

        val classHello = Class.forName("com.kakaopaysec.proxy.pureproxy.proxy.jdkdynamic.Hello")
        val target = Hello()

        val methodA = classHello.getMethod("callA")
        dynamicCall(methodA, target)

        val methodB = classHello.getMethod("callB")
        dynamicCall(methodB, target)
    }

    private fun dynamicCall(method: Method, target: Any) {
        logger.info { "start" }
        val result = method.invoke(target)
        logger.info { "result = $result" }
    }
}

class Hello {

    fun callA(): String {
        logger.info { "callA" }
        return "A"
    }

    fun callB(): String {
        logger.info { "callB" }
        return "B"
    }
}
