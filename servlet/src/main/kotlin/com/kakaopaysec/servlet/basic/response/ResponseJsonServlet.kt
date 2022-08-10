package com.kakaopaysec.servlet.basic.response

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.kakaopaysec.servlet.basic.RequestBodyServlet
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "responseJsonServlet", urlPatterns = ["/response-json"])
class ResponseJsonServlet: HttpServlet() {
    override fun service(request: HttpServletRequest?, response: HttpServletResponse?) {

        response?.contentType = "application/json"
        response?.characterEncoding = "utf-8"

        val helloData = RequestBodyServlet.HelloData("kim", 20)

        val mapper = ObjectMapper().registerKotlinModule()
        val responseBody = mapper.writeValueAsString(helloData)

        val writer = response?.writer
        writer?.println(responseBody)
    }
}
