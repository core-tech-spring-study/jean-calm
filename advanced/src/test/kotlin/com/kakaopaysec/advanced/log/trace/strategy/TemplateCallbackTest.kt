package com.kakaopaysec.advanced.log.trace.strategy

import com.kakaopaysec.advanced.log.trace.strategy.code.template.Callback
import com.kakaopaysec.advanced.log.trace.strategy.code.template.TimeLogTemplate
import mu.KotlinLogging
import org.junit.jupiter.api.Test

private val logger = KotlinLogging.logger {}

class TemplateCallbackTest {


    @Test
    fun callbackV1() {

        val template = TimeLogTemplate()
        template.execute(object: Callback {
            override fun call() {
                logger.info { "비즈니스 로직1 실행" }
            }
        })

        template.execute(object: Callback {
            override fun call() {
                logger.info { "비즈니스 로직2 실행" }
            }
        })
    }
}
