package com.kakaopaysec.aop.internal

import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class CallServiceV0 {

    fun external() {
        logger.info { "call external" }
        internal() // 내부 메서드 호출
    }

    fun internal() {
        logger.info { "call internal" }
    }
}
