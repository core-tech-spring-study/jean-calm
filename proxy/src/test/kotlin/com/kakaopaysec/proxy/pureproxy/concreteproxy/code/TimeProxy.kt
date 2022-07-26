package com.kakaopaysec.proxy.pureproxy.concreteproxy.code

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class TimeProxy(
    private val concreteLogic: ConcreteLogic
): ConcreteLogic() {

    @Override
    override fun operation(): String {
        logger.info { "TimeDecorator 실행" }
        val startTime = System.currentTimeMillis()

        val result = concreteLogic.operation()

        val endTime = System.currentTimeMillis()
        val resulTime = endTime - startTime
        logger.info { "TimeDecorator 종료 resultTime = ${resulTime}ms" }
        return result
    }
}
