package com.kakaopaysec.advanced.log.trace.strategy.code.template

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class TimeLogTemplate {

    fun execute(callback: Callback) {
        val startTime = System.currentTimeMillis()

        // 비즈니스 로직 실행
        callback.call()

        // 비즈니스 로직 종료
        val endTime = System.currentTimeMillis()
        val resultTime = endTime - startTime
        logger.info { "resultTime=$resultTime" }
    }
}
