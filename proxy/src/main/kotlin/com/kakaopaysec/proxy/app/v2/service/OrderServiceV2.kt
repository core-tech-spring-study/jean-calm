package com.kakaopaysec.proxy.app.v2.service

import com.kakaopaysec.proxy.app.v2.repository.OrderRepositoryV2

class OrderServiceV2(
    private val orderRepository: OrderRepositoryV2
) {

    fun orderItem(itemId: String) {
        orderRepository.save(itemId)
    }
}
