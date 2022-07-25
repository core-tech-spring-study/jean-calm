package com.kakaopaysec.proxy.app.v2.api

import com.kakaopaysec.proxy.app.v2.service.OrderServiceV2
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

// 기본적으로 @Controller or @RequestMapping 어노테이션을 스프링 컨트롤러로 인식함.
// @Controller 어노테이션은 내부적으로 @Component 어노테이션을 가지고 있어서
// 컴포넌트 스캔시에 자동으로 빈 등록이 됩니다. 수동등록을 위해 @RequestMapping을 사용함.
@RequestMapping
@ResponseBody
class OrderApiV2(
    private val orderService: OrderServiceV2
) {

    @GetMapping("/v2/request")
    fun request(itemId: String): String {
        orderService.orderItem(itemId)
        return "OK"
    }

    @GetMapping("/v2/no-log")
    fun noLog(): String {
        return "OK"
    }
}
