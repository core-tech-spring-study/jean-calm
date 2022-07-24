package com.kakaopaysec.proxy.log.v0.service

import com.kakaopaysec.proxy.log.v0.repository.OrderRepositoryV0
import org.springframework.stereotype.Service

@Service
class OrderServiceV0(
    private val orderRepositoryV0: OrderRepositoryV0
) {

    fun orderItem(itemId: String) {
        orderRepositoryV0.save(itemId)
    }
}

