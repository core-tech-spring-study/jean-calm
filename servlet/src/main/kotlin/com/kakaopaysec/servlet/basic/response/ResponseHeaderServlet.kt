package com.kakaopaysec.servlet.basic.response

import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "responseHeaderServlet", urlPatterns = ["/response-header"])
class ResponseHeaderServlet: HttpServlet() {

    override fun service(request: HttpServletRequest?, response: HttpServletResponse?) {
        // [status line]i
        response?.status = HttpServletResponse.SC_OK

        // [response header]
        response?.contentType = "text/plain"
        response?.characterEncoding = "UTF-8"
        response?.setHeader("Cache-Control", "no-cache, no-store, must-revalidation")
        response?.setHeader("Pragma", "no-cache")
        response?.setHeader("my-header", "hello")

        val cookie = Cookie("myCookie", "Good")
        cookie.maxAge = 60

        response?.addCookie(cookie)
        response?.status = HttpServletResponse.SC_FOUND
        response?.sendRedirect( "/hello-form.html")

        val writer = response?.writer
        writer?.println("임준영")
    }
}
