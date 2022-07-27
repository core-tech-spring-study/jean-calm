package com.kakaopaysec.proxy.pureproxy.proxy.jdkdynamic.code

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class AImpl: AInterface {

    override fun call(): String {
        logger.info { "A 호출" }
        return "a"
    }
}
