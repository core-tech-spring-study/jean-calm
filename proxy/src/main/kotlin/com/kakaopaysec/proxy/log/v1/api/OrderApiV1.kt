package com.kakaopaysec.proxy.log.v1.api

import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.hellotrace.HelloTraceV1
import com.kakaopaysec.proxy.log.v1.service.OrderServiceV1
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiV1(
    private val orderServiceV1: OrderServiceV1,
    private val trace: HelloTraceV1
) {

    @GetMapping("/v1/request")
    fun order(itemId: String): String {

        var status: TraceStatus? = null

        try {
            status = trace.begin("OrderServiceV1.request()")
            orderServiceV1.orderItem(itemId)
            trace.end(status)
            return "OK"
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }
    }
}
