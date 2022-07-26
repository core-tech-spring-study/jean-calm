package com.kakaopaysec.proxy.log.v5.service

import com.kakaopaysec.proxy.log.trace.callback.TraceCallback
import com.kakaopaysec.proxy.log.trace.callback.TraceTemplate
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import com.kakaopaysec.proxy.log.v5.repository.OrderRepositoryV5
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class OrderServiceV5(
    private val orderRepositoryV5: OrderRepositoryV5,
    private val trace: LogTrace
) {

    private val traceTemplate: TraceTemplate = TraceTemplate(trace)

    fun orderItem(itemId: String) {
        return traceTemplate.execute("OrderServiceV5.orderItem()", object: TraceCallback<Unit> {
            override fun call() {
                orderRepositoryV5.save(itemId)
            }
        })
    }
}
