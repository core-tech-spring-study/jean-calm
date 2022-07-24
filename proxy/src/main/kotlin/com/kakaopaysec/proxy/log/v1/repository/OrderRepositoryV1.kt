package com.kakaopaysec.proxy.log.v1.repository

import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.hellotrace.HelloTraceV1
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class OrderRepositoryV1(
    private val trace: HelloTraceV1
) {

    fun save(itemId: String) {
        var status: TraceStatus? = null

        try {
            status = trace.begin("OrderRepositoryV1.save()")

            if (itemId == "ex") {
                throw IllegalStateException("예외발생!")
            }

            sleep(1000L)
            trace.end(status)
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }
    }

    fun sleep(millis: Long) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
