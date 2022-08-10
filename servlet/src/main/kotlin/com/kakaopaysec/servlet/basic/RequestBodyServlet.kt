package com.kakaopaysec.servlet.basic

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.util.StreamUtils
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "requestBodyServlet", urlPatterns = ["/request-body"])
class RequestBodyServlet: HttpServlet() {
    override fun service(request: HttpServletRequest?, response: HttpServletResponse?) {

        println("[Request Body 조회] - start")

        val inputStream = request?.inputStream
        val message = StreamUtils.copyToString(inputStream, Charsets.UTF_8)
        println("message = $message")

        val mapper = ObjectMapper().registerKotlinModule()

        val helloData = mapper.readValue(message, HelloData::class.java)
        println("HelloData = $helloData")

        println("[Request Body 조회] - end")
        println()

    }

    data class HelloData(var name: String?, val age: Int)
}
