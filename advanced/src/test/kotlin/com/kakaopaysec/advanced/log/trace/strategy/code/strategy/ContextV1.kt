package com.kakaopaysec.advanced.log.trace.strategy.code.strategy

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}


/**
 * 필드에 전략을 보관하는 방식
 */
class ContextV1(
    private val strategy: Strategy
) {


    fun execute() {
        val startTime = System.currentTimeMillis()

        // 비즈니스 로직 실행
        strategy.call()

        // 비즈니스 로직 종료
        val endTime = System.currentTimeMillis()
        val resultTime = endTime - startTime
        logger.info { "resultTime=$resultTime" }
    }
}
