package com.kakaopaysec.core.order.service

import com.kakaopaysec.core.order.domain.Order

interface OrderService {
    fun createOrder(memberId: Long, itemName: String, itemPrice: Int): Order
}
