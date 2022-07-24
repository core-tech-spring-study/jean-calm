package com.kakaopaysec.proxy.log.v3.service

import com.kakaopaysec.proxy.log.trace.TraceStatus
import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import com.kakaopaysec.proxy.log.v3.repository.OrderRepositoryV3
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class OrderServiceV3(
    private val orderRepositoryV3: OrderRepositoryV3,
    @Qualifier("threadLocalLogTrace") private val trace: LogTrace
) {

    fun  orderItem(itemId: String) {
        var status: TraceStatus? = null

        try {
            status = trace.begin("OrderServiceV3.orderItem()")
            orderRepositoryV3.save(itemId)
            trace.end(status)
        } catch (e: Exception) {
            trace.exception(status!!, e)
            throw e
        }
    }
}
