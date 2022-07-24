package com.kakaopaysec.proxy.log.v5.api

import com.kakaopaysec.proxy.log.trace.callback.TraceCallback
import com.kakaopaysec.proxy.log.trace.callback.TraceTemplate
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import com.kakaopaysec.proxy.log.v5.service.OrderServiceV5
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiV5(
    private val orderServiceV5: OrderServiceV5,
    @Qualifier("threadLocalLogTrace") private val trace: LogTrace
) {

    private val traceTemplate: TraceTemplate = TraceTemplate(trace)

    @GetMapping("/v5/request")
    fun request(itemId: String): String {
        return traceTemplate.execute("OrderApiV5.request()", object: TraceCallback<String> {
            override fun call(): String {
                orderServiceV5.orderItem(itemId)
                return "OK"
            }
        })
    }
}
