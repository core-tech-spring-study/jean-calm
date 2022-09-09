package com.kakaopaysec.exception.interceptor

import mu.KotlinLogging
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

const val LOG_ID = "logId"
private val logger = KotlinLogging.logger {}

class LogInterceptor: HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestURI = request.requestURI

        val uuid = UUID.randomUUID().toString()
        request.setAttribute(LOG_ID, uuid)

        logger.info { "REQUEST {$uuid} {${request.dispatcherType}} {$requestURI} {$handler}" }

        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        logger.info { "postHandle {$modelAndView}" }
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        val requestURI = request.requestURI
        val logId = request.getAttribute(LOG_ID)
        logger.info { "RESPONSE {$logId} {${request.dispatcherType}} {$requestURI}" }
        ex?.let { logger.error { "afterCompletion error!! $ex" } }
    }
}
