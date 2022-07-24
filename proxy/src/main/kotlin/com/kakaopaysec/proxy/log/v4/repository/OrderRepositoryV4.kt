package com.kakaopaysec.proxy.log.v4.repository

import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import com.kakaopaysec.proxy.log.trace.template.AbstractTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class OrderRepositoryV4(
    @Qualifier("threadLocalLogTrace") private val trace: LogTrace
) {

    fun save(itemId: String) {

        val abstractTemplate = object: AbstractTemplate<Unit>(trace) {
            override fun call() {

                if (itemId == "ex") {
                    throw IllegalStateException("예외발생!")
                }

                sleep(1000L)
            }
        }

        abstractTemplate.execute("OrderRepositoryV4.save()")
    }

    fun sleep(millis: Long) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
