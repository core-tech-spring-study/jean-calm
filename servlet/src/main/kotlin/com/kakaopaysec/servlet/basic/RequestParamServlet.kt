package com.kakaopaysec.servlet.basic

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "requestParamServlet", urlPatterns = ["/request-param"])
class RequestParamServlet: HttpServlet() {

    override fun service(request: HttpServletRequest?, response: HttpServletResponse?) {
        println("[전체 파라미터 조회] - start")

        request?.parameterNames!!.asIterator()
            .forEach {
                println("$it = ${request.getParameter(it)}")
            }

        println("[전체 파라미터 조회] - end")
        println()
    }
}
