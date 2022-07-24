package com.kakaopaysec.proxy.log.v2.service

import com.kakaopaysec.proxy.log.trace.TraceId
import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.hellotrace.HelloTraceV2
import com.kakaopaysec.proxy.log.v2.repository.OrderRepositoryV2
import org.springframework.stereotype.Service

@Service
class OrderServiceV2(
    private val orderRepositoryV2: OrderRepositoryV2,
    private val trace: HelloTraceV2
) {

    fun orderItem(traceId: TraceId, itemId: String) {
        var status: TraceStatus? = null

        try {
            status = trace.beginSync(traceId,"OrderServiceV2.orderItem()")
            orderRepositoryV2.save(status.traceId, itemId)
            trace.end(status)
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }
    }
}
