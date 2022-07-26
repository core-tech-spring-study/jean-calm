package com.kakaopaysec.proxy.pureproxy.proxy.code

import mu.KotlinLogging
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

class RealSubject: Subject {

    override fun operation(): String {
        logger.info { "실제 객체 호출" }
        sleep(1000)
        return "data"
    }

    private fun sleep(millis: Long) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
