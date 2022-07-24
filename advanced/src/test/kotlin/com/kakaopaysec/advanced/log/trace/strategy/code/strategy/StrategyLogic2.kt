package com.kakaopaysec.advanced.log.trace.strategy.code.strategy

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

open class StrategyLogic2: Strategy {

    override fun call() {
        logger.info { "비즈니스 로직2 실행" }
    }
}
