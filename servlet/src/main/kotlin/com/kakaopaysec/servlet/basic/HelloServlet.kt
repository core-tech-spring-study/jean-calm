package com.kakaopaysec.servlet.basic

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "helloServlet", urlPatterns = ["/hello"])
class HelloServlet: HttpServlet() {

    override fun service(request: HttpServletRequest?, response: HttpServletResponse?) {
        println("HelloServlet.service")
        println("request = $request")
        println("response = $response")

        val userName = request?.getParameter("userName")
        println("userName: $userName")

        response?.contentType = "text/plain"
        response?.characterEncoding = "utf-8"
        response?.writer?.write("hello $userName")
    }
}
