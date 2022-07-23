package com.kakaopaysec.advanced.log.v2.api

import com.kakaopaysec.advanced.log.trace.TraceStatus
import com.kakaopaysec.advanced.log.trace.hellotrace.HelloTraceV2
import com.kakaopaysec.advanced.log.v2.service.OrderServiceV2
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiV2(
    private val orderServiceV2: OrderServiceV2,
    private val trace: HelloTraceV2
) {

    @GetMapping("/v2/request")
    fun order(itemId: String): String {

        var status: TraceStatus? = null

        try {
            status = trace.begin("OrderApiV2.request()")
            orderServiceV2.orderItem(status.traceId, itemId)
            trace.end(status)
            return "OK"
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }
    }
}
