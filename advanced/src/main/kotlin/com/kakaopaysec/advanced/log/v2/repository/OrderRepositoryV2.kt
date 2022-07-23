package com.kakaopaysec.advanced.log.v2.repository

import com.kakaopaysec.advanced.log.trace.TraceId
import com.kakaopaysec.advanced.log.trace.TraceStatus
import com.kakaopaysec.advanced.log.trace.hellotrace.HelloTraceV2
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class OrderRepositoryV2(
    private val trace: HelloTraceV2
) {

    fun save(traceId: TraceId, itemId: String) {
        var status: TraceStatus? = null

        try {
            status = trace.beginSync(traceId,"OrderRepositoryV2.save()")

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
