package com.kakaopaysec.exception.filter

import mu.KotlinLogging
import java.util.*
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

private val logger = KotlinLogging.logger {}

class LogFilter : Filter {

    override fun init(filterConfig: FilterConfig?) {
        logger.info { "log filter init" }
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        val httpRequest = request as HttpServletRequest
        val url = httpRequest.requestURI
        val uuid = UUID.randomUUID().toString()

        try {
            logger.info { "REQUEST - [$uuid] [${request.dispatcherType}] [$url]" }
            chain?.doFilter(request, response)
        } catch (e: Exception) {
            throw e
        } finally {
            logger.info { "RESPONSE - [$uuid] [${request.dispatcherType}] [$url]" }
        }
    }

    override fun destroy() {
        logger.info { "log filter destroy" }
    }
}
