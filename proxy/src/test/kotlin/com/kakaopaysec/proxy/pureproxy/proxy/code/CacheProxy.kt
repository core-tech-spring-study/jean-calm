package com.kakaopaysec.proxy.pureproxy.proxy.code

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class CacheProxy(
    private val target: Subject
): Subject {

    private var cacheValue: String? = null

    override fun operation(): String {

        logger.info { "프록시 호출!" }

        return cacheValue?.let {
            cacheValue
        } ?: kotlin.run {
            cacheValue = target.operation()
            cacheValue!!
        }
    }
}
