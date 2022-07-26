package com.kakaopaysec.proxy.config.v1_proxy.concrete_proxy.repository

import com.kakaopaysec.proxy.app.v2.repository.OrderRepositoryV2
import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace

class OrderRepositoryConcreteProxy(
    private val target: OrderRepositoryV2,
    private val trace: LogTrace
): OrderRepositoryV2() {

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
