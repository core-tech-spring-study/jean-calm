package com.kakaopaysec.proxy.app.v1.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@RequestMapping
@ResponseBody
interface OrderApiV1 {

    @GetMapping("/v1/request")
    fun request(@RequestParam("itemId") itemId: String): String

    @GetMapping("/v1/no-log")
    fun noLog(): String
}
