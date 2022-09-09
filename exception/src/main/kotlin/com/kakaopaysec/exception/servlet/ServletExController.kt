package com.kakaopaysec.exception.servlet

import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletResponse

private val logger = KotlinLogging.logger {}

@Controller
class ServletExController {

    @GetMapping(value = ["/error-ex"])
    fun errorEx() {
        throw RuntimeException("예외발생!")
    }

    @GetMapping(value = ["/error-404"])
    fun error404(response: HttpServletResponse) {
        response.sendError(404 ,"404 오류!")
    }

    @GetMapping(value = ["/error-400"])
    fun error400(response: HttpServletResponse) {
        response.sendError(400 ,"400 오류!")
    }

    @GetMapping(value = ["/error-500"])
    fun error500(response: HttpServletResponse) {
        response.sendError(500)
    }
}
