package com.kakaopaysec.proxy.config.v1_proxy.interface_proxy.repository

import com.kakaopaysec.proxy.app.v1.repository.OrderRepositoryV1
import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import org.springframework.beans.factory.annotation.Qualifier

class OrderRepositoryInterfaceProxy(
    private val target: OrderRepositoryV1,
    private val trace: LogTrace
) : OrderRepositoryV1 {

    override fun save(itemId: String) {

        var status: TraceStatus? = null

        try {
            status = trace.begin("OrderRepository.save()")

            // target 호출
            target.save(itemId)

            trace.end(status)
        } catch (e: Exception) {
            trace.end(status!!)
            throw e
        }
    }
}
