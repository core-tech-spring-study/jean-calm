package com.kakaopaysec.proxy.pureproxy.decorator.code

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class RealComponent: Component {
    override fun operation(): String {
        logger.info { "RealComponent 실행" }
        return "data"
    }
}
