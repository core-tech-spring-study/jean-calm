package com.kakaopaysec.proxy.config.v1_proxy.interface_proxy.service

import com.kakaopaysec.proxy.app.v1.service.OrderServiceV1
import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace

class OrderServiceInterfaceProxy(
    private val target: OrderServiceV1,
    private val trace: LogTrace
): OrderServiceV1 {

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
