package com.kakaopaysec.aop.internal

import mu.KotlinLogging
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class CallServiceV2(
    private val callServiceProvider: ObjectProvider<CallServiceV2>
) {

    fun external() {
        logger.info { "call external" }
        val callServiceV2 = callServiceProvider.getObject() as CallServiceV2
        callServiceV2.internal()
    }

    fun internal() {
        logger.info { "call internal" }
    }
}
