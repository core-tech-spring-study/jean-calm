package com.kakaopaysec.advanced.log.v0.api

import com.kakaopaysec.advanced.log.v0.service.OrderServiceV0
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiV0(
    private val orderServiceV0: OrderServiceV0
) {

    @GetMapping("/v0/request")
    fun order(itemId: String): String {
        orderServiceV0.orderItem(itemId)
        return "OK"
    }
}
