package com.kakaopaysec.proxy.log.v1.service

import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.hellotrace.HelloTraceV1
import com.kakaopaysec.proxy.log.v1.repository.OrderRepositoryV1
import org.springframework.stereotype.Service

@Service
class OrderServiceV1(
    private val orderRepositoryV1: OrderRepositoryV1,
    private val trace: HelloTraceV1
) {

    fun orderItem(itemId: String) {
        var status: TraceStatus? = null

        try {
            status = trace.begin("OrderServiceV1.orderItem()")
            orderRepositoryV1.save(itemId)
            trace.end(status)
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }
    }
}
