package com.kakaopaysec.advanced.log.trace.threadlocal.code

import mu.KotlinLogging
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

class ThreadLocalService {

    private val nameStore = ThreadLocal<String>()

    fun logic(name: String): String {
        logger.info { "저장 name = $name ㅡ> nameStore = ${nameStore.get()}" }
        nameStore.set(name)
        TimeUnit.MILLISECONDS.sleep(1000)
        logger.info { "조회 nameStore = ${nameStore.get()}" }
        return nameStore.get()
    }
}
