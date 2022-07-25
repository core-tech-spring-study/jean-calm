package com.kakaopaysec.proxy.app.v3.repository

import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class OrderRepositoryV3 {

    fun save(itemId: String) {
        if (itemId == "ex") {
            throw IllegalStateException("예외 발생!")
        }
        sleep(1000)
    }

    private fun sleep(millis: Long) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
