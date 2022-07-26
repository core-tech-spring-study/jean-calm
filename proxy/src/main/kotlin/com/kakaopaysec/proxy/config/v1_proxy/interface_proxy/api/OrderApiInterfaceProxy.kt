package com.kakaopaysec.proxy.config.v1_proxy.interface_proxy.api

import com.kakaopaysec.proxy.app.v1.api.OrderApiV1
import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import org.springframework.beans.factory.annotation.Qualifier

class OrderApiInterfaceProxy(
    private val target: OrderApiV1,
    private val trace: LogTrace
): OrderApiV1 {

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
