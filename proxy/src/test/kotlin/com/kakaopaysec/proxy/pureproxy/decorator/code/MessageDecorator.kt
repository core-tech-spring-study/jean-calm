package com.kakaopaysec.proxy.pureproxy.decorator.code

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class MessageDecorator(
    private val component: Component
): Component {

    override fun operation(): String {
        logger.info { "MessageDecorator 실행" }
        val decoResult = component.operation()
        logger.info { "MessageDecorator 꾸미기 적용" }
        return "******* $decoResult *******"
    }
}
