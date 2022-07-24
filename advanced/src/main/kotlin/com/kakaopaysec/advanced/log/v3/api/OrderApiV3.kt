package com.kakaopaysec.advanced.log.v3.api

import com.kakaopaysec.advanced.log.trace.TraceStatus
import com.kakaopaysec.advanced.log.trace.logtrace.LogTrace
import com.kakaopaysec.advanced.log.v3.service.OrderServiceV3
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiV3(
    private val orderServiceV3: OrderServiceV3,
    @Qualifier("threadLocalLogTrace") private val trace: LogTrace
) {

    @GetMapping("/v3/request")
    fun order(itemId: String): String {
        var status: TraceStatus? = null

        try {
            status = trace.begin("OrderApiV3.request()")
            orderServiceV3.orderItem(itemId)
            trace.end(status)
            return "OK"
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }
    }
}
