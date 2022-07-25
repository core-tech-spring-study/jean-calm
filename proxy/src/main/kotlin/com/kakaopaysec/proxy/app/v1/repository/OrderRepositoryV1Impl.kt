package com.kakaopaysec.proxy.app.v1.repository

import java.util.concurrent.TimeUnit

class OrderRepositoryV1Impl: OrderRepositoryV1 {

    override fun save(itemId: String) {
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
