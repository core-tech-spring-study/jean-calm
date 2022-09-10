package com.kakaopaysec.exception.resolver

import mu.KotlinLogging
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.Exception

private val logger = KotlinLogging.logger {}

class MyHandlerExceptionResolver:  HandlerExceptionResolver {

    override fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any?,
        ex: Exception
    ): ModelAndView? {

        try {
            if (ex is IllegalArgumentException) {
                logger.info { "IllegalArgumentException resolver to 400" }
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.message)
                return ModelAndView()
            }
        } catch (e: Exception) {
            logger.error { "ERROR $e" }
        }

        return null
    }
}
