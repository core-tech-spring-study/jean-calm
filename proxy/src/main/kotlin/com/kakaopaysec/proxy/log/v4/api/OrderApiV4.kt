package com.kakaopaysec.proxy.log.v4.api

import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import com.kakaopaysec.proxy.log.trace.template.AbstractTemplate
import com.kakaopaysec.proxy.log.v4.service.OrderServiceV4
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiV4(
    private val orderServiceV4: OrderServiceV4,
    @Qualifier("threadLocalLogTrace") private val trace: LogTrace
) {

    @GetMapping("/v4/request")
    fun request(itemId: String): String {
        val template = object: AbstractTemplate<String>(trace) {
            override fun call(): String {
                orderServiceV4.orderItem(itemId)
                return "OK"
            }
        }
        return template.execute("OrderApiV4.request()")
    }
}
