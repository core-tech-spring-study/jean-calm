package com.kakaopaysec.aop.internal

import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class InternalService {

    fun internal() {
        logger.info { "call internal" }
    }
}
