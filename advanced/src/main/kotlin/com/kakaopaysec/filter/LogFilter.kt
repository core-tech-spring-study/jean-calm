package com.kakaopaysec.filter

import mu.KotlinLogging
import java.util.*
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

private val logger = KotlinLogging.logger {}

class LogFilter: Filter {

    override fun init(filterConfig: FilterConfig?) {
        logger.info { "log filter init" }
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {

        val httpRequest = request as HttpServletRequest
        val url = httpRequest.requestURI
        val uuid = UUID.randomUUID().toString()

        try {
            logger.info { "REQUEST [$uuid] [$url]" }
            chain.doFilter(request, response)
        } catch (e: Exception) {
            logger.error { "filter error" }
            throw e
        } finally {
            logger.info { "RESPONSE [$uuid] [$url]" }
        }
    }

    override fun destroy() {
        logger.info { "log filter destroy" }
    }
}
