package com.kakaopaysec.core.web

import com.kakaopaysec.core.common.MyLogger
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

@Controller
class LogDemoController(
    private val logDemoService: LogDemoService,
    private val myLogger: MyLogger
) {

    @RequestMapping("log-demo")
    @ResponseBody
    fun logDemo(request: HttpServletRequest): String {
        val requestUrl = request.requestURL.toString()

        println("myLogger = ${myLogger.javaClass}")
        myLogger.requestUrl = requestUrl

        myLogger.log("controller test")
        logDemoService.logic("testId")

        return "OK"
    }
}
