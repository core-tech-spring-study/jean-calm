package com.kakaopaysec.proxy.pureproxy.decorator.code

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class ConcreteDecorator(
    private val component: Component
): AbstractDecorator(component) {

    override fun operation(): String {
        logger.info { "concrete Decorator!" }
        return super.operation()
    }
}
