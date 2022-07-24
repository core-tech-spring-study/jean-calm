package com.kakaopaysec.proxy.log.v0.repository

import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class OrderRepositoryV0 {

    fun save(itemId: String) {
        if (itemId == "ex") {
            throw IllegalStateException("예외발생")
        }
        sleep(1000L)
    }

    fun sleep(millis: Long) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
