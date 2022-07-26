package com.kakaopaysec.aop.internal

import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class CallServiceV3(
    private val internalService: InternalService
) {

    fun external() {
        logger.info { "call external" }
        internalService.internal()
    }
}
