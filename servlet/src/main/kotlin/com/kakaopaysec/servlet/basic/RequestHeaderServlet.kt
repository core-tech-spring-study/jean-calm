package com.kakaopaysec.servlet.basic

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "requestHeaderServlet", urlPatterns = ["/request-header"])
class RequestHeaderServlet : HttpServlet() {

    override fun service(request: HttpServletRequest?, response: HttpServletResponse?) {
        printStartLine(request!!)
        printHeader(request!!)
        printHeaderUtils(request!!)
    }

    private fun printStartLine(request: HttpServletRequest) {
        println("--- REQUEST-LINE - start ---")
        println("request.getMethod() = ${request.method}") //GET
        println("request.getProtocol() = ${request.protocol}")
        println("request.getScheme() = ${request.scheme}") //http
        println("request.getRequestURL() = ${request.requestURL}")
        println("request.getRequestURI() = ${request.requestURI}")
        println("request.getQueryString() ${request.queryString}")
        println("request.isSecure() = ${request.isSecure}")
        println("--- REQUEST-LINE - end ---")
        println()
    }

    private fun printHeader(request: HttpServletRequest) {
        println("--- Headers - start ---")

        request.headerNames.asIterator()
            .forEachRemaining {
                println("$it: ${request.getHeader(it)}")
            }

        println("--- Headers - end ---")
        println()
    }

    private fun printHeaderUtils(request: HttpServletRequest) {
        println("--- Header 편의 조회 start ---")
        println("[Host 편의 조회]")
        println("request.getServerName() = ${request.serverName}")
        println("request.getServerName() = ${request.serverPort}")
        println()

        println("[Accept-Language 편의 조회]")
        request.locales.asIterator()
            .forEachRemaining {
                println("locale = $it")
            }
        println()

        println("[cookie 편의 조회]")

        if (request.cookies != null) {
            request.cookies.forEach {
                println("${it.name} : ${it.value}")
            }
        }
        println()

        println("[Content 편의 조회]")
        println("request.getContentType() = ${request.contentType}")
        println("request.getContentLength() = ${request.contentLength}")
        println("request.getCharacterEncoding() = ${request.characterEncoding}")

        println("--- Headers 편의 조회 end ---")
        println()
    }
}
