package com.kakaopaysec.proxy.pureproxy.proxy.common.service

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

open class ConcreteService {
    open fun call() {
        logger.info { "ConcreteService 호출" }
    }
}
