package com.kakaopaysec.advanced.log.trace.threadlocal.code

import mu.KotlinLogging
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

class FieldService {

    var nameStore: String? = null

    fun logic(name: String): String {
        logger.info { "저장 name = $name ㅡ> nameStore = $nameStore" }
        nameStore = name
        TimeUnit.MILLISECONDS.sleep(1000)
        logger.info { "조회 nameStore = $nameStore" }
        return nameStore!!
    }


}
