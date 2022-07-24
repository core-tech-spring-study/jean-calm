package com.kakaopaysec.advanced.log.v5.service

import com.kakaopaysec.advanced.log.trace.callback.TraceCallback
import com.kakaopaysec.advanced.log.trace.callback.TraceTemplate
import com.kakaopaysec.advanced.log.trace.logtrace.LogTrace
import com.kakaopaysec.advanced.log.trace.template.AbstractTemplate
import com.kakaopaysec.advanced.log.v4.repository.OrderRepositoryV4
import com.kakaopaysec.advanced.log.v5.repository.OrderRepositoryV5
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class OrderServiceV5(
    private val orderRepositoryV5: OrderRepositoryV5,
    @Qualifier("threadLocalLogTrace") private val trace: LogTrace
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
