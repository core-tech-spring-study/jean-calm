package com.kakaopaysec.proxy.pureproxy.proxy.common.service

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

open class ServiceImpl : ServiceInterface {

    override fun save() {
        logger.info { "save 호출" }
    }

    override fun find() {
        logger.info { "find 호출" }
    }
}
