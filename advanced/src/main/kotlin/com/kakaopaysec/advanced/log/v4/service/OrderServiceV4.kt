package com.kakaopaysec.advanced.log.v4.service

import com.kakaopaysec.advanced.log.trace.TraceId
import com.kakaopaysec.advanced.log.trace.TraceStatus
import com.kakaopaysec.advanced.log.trace.logtrace.LogTrace
import com.kakaopaysec.advanced.log.trace.template.AbstractTemplate
import com.kakaopaysec.advanced.log.v4.repository.OrderRepositoryV4
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class OrderServiceV4(
    private val orderRepositoryV4: OrderRepositoryV4,
    @Qualifier("threadLocalLogTrace") private val trace: LogTrace
) {

    fun orderItem(itemId: String) {

        val abstractTemplate = object: AbstractTemplate<Unit> (trace) {
            override fun call() {
                orderRepositoryV4.save(itemId)
            }
        }

        abstractTemplate.execute("OrderServiceV4.orderItem()")
    }
}
