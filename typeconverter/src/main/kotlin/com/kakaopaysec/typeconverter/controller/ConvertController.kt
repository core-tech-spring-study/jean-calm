package com.kakaopaysec.typeconverter.controller

import com.kakaopaysec.typeconverter.type.IpPort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ConvertController {

    @GetMapping("/hello-v2")
    fun helloV2(@RequestParam data: Int): String {
        println("data = $data")
        return "ok"
    }

    @GetMapping("/ip-port")
    fun ipPort(@RequestParam ipPort: IpPort): String {
        println("ipPort = $ipPort")
        return "ok"
    }
}
