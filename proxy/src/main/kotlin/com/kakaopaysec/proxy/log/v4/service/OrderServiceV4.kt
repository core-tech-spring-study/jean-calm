package com.kakaopaysec.proxy.log.v4.service

import com.kakaopaysec.proxy.log.trace.logtrace.LogTrace
import com.kakaopaysec.proxy.log.trace.template.AbstractTemplate
import com.kakaopaysec.proxy.log.v4.repository.OrderRepositoryV4
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class OrderServiceV4(
    private val orderRepositoryV4: OrderRepositoryV4,
    private val trace: LogTrace
) {

    fun orderItem(itemId: String) {

        val abstractTemplate = object: AbstractTemplate<Unit>(trace) {
            override fun call() {
                orderRepositoryV4.save(itemId)
            }
        }

        abstractTemplate.execute("OrderServiceV4.orderItem()")
    }
}
