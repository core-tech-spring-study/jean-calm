package com.kakaopaysec.aop.order.service

import com.kakaopaysec.aop.order.repository.OrderRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {

    fun orderItem(itemId: String) {
        logger.info { "[orderService] 실행" }
        orderRepository.save(itemId)
    }
}
