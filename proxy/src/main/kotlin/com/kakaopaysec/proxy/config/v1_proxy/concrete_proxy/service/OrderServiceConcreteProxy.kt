package com.kakaopaysec.proxy.config.v1_proxy.concrete_proxy.service

import com.kakaopaysec.proxy.app.v2.service.OrderServiceV2
import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace


class OrderServiceConcreteProxy(
    private val target: OrderServiceV2,
    private val trace: LogTrace
): OrderServiceV2(null) {

    override fun orderItem(itemId: String) {
        var status: TraceStatus? = null

        try {
            status = trace.begin("OrderService.orderItem()")

            // target 호출
            target.orderItem(itemId)

            trace.end(status)
        } catch (e: Exception) {
            trace.end(status!!)
            throw e
        }
    }
}
