package com.kakaopaysec.proxy.pureproxy.decorator.code

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class TimeDecorator(
    private val component: Component
): Component {

    override fun operation(): String {
        logger.info { "TimeDecorator 실행" }
        val startTime = System.currentTimeMillis()
        val result = component.operation()
        val endTime = System.currentTimeMillis()
        val resulTime = endTime - startTime
        logger.info { "TimeDecorator 종료 resultTime = ${resulTime}ms" }
        return result
    }
}
