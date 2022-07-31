package com.kakaopaysec.aop.order.repository

import mu.KotlinLogging
import org.springframework.stereotype.Repository

private val logger = KotlinLogging.logger {}

@Repository
class OrderRepository {

    fun save(itemId: String): String {
        logger.info { "[orderRepository] 실행" }

        if (itemId == "ex") {
            throw IllegalStateException("예외 발생!")
        }

        return "OK"
    }
}
