package com.kakaopaysec.proxy.app.v1.api

import com.kakaopaysec.proxy.app.v1.service.OrderServiceV1

class OrderApiV1Impl(
    private val orderService: OrderServiceV1
): OrderApiV1 {

    override fun request(itemId: String): String {
        orderService.orderItem(itemId)
        return "OK"
    }

    override fun noLog(): String {
        return "OK"
    }
}
