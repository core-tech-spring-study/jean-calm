package com.kakaopaysec.advanced.log.trace.template.code

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class SubClassLogic2: AbstractTemplate() {

    override fun call() {
        logger.info { "비즈니스 로직2 실행" } // 함수를 파라미터로 받기 때문에 lazy 연산 적용 가능함.
    }
}
