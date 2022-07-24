package com.kakaopaysec.advanced.log.v5.repository

import com.kakaopaysec.advanced.log.trace.callback.TraceCallback
import com.kakaopaysec.advanced.log.trace.callback.TraceTemplate
import com.kakaopaysec.advanced.log.trace.logtrace.LogTrace
import com.kakaopaysec.advanced.log.trace.template.AbstractTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class OrderRepositoryV5(
    @Qualifier("threadLocalLogTrace") private val trace: LogTrace
) {

    private val traceTemplate: TraceTemplate = TraceTemplate(trace)

    fun save(itemId: String) {
        return traceTemplate.execute("OrderRepositoryV5.save()", object: TraceCallback<Unit> {
            override fun call() {

                if (itemId == "ex") {
                    throw IllegalStateException("예외발생!")
                }

                sleep(1000L)
            }
        })
    }

    fun sleep(millis: Long) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
