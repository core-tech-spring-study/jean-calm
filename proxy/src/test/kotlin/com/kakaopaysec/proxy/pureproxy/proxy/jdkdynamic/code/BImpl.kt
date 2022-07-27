package com.kakaopaysec.proxy.pureproxy.proxy.jdkdynamic.code

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class BImpl: BInterface {
    override fun call(): String {
        logger.info { "B 호출" }
        return "B"
    }
}
