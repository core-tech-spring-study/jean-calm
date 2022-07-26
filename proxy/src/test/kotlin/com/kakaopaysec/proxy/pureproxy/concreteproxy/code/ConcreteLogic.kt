package com.kakaopaysec.proxy.pureproxy.concreteproxy.code

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

open class ConcreteLogic {
    open fun operation(): String {
        logger.info { "ConcreteLogic 실행" }
        return "data"
    }
}
