package com.kakaopaysec.advanced.log.trace.template.code

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

abstract class AbstractTemplate {

    fun execute() {

        val startTime = System.currentTimeMillis()

        // 비즈니스 로직 실행
        call() // 상속

        // 비즈니스 로직 종료
        val endTime = System.currentTimeMillis()
        val resultTime = endTime - startTime
        logger.info { "resultTime=$resultTime" }

    }

    protected abstract fun call()
}
