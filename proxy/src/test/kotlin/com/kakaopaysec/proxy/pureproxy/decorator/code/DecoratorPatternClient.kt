package com.kakaopaysec.proxy.pureproxy.decorator.code

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class DecoratorPatternClient(
    private val component: Component
) {

    fun execute() {
        val result = component.operation()
        logger.info { "result = $result" }
    }
}
