package com.kakaopaysec.proxy.config.v1_proxy.concrete_proxy.api

import com.kakaopaysec.proxy.app.v2.api.OrderApiV2
import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace

class OrderApiConcreteProxy(
    private val target: OrderApiV2,
    private val trace: LogTrace
): OrderApiV2(null) {

    override fun request(itemId: String): String {
        var status: TraceStatus? = null

        try {
            status = trace.begin("OrderApi.request()")

            // target 호출
            target.request(itemId)

            trace.end(status)

            return "OK"
        } catch (e: Exception) {
            trace.end(status!!)
            throw e
        }
    }

    override fun noLog(): String {
        return target.noLog()
    }
}
