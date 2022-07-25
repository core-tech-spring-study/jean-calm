package com.kakaopaysec.proxy.app.v3.api

import com.kakaopaysec.proxy.app.v3.service.OrderServiceV3
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiV3(
    private val orderService: OrderServiceV3
) {

    @GetMapping("/v3/request")
    fun request(itemId: String): String {
        orderService.orderItem(itemId)
        return "OK"
    }

    @GetMapping("/v3/no-log")
    fun noLog(): String {
        return "OK"
    }
}
