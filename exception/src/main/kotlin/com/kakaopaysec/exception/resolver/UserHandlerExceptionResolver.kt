package com.kakaopaysec.exception.resolver

import com.fasterxml.jackson.databind.ObjectMapper
import com.kakaopaysec.exception.exception.UserException
import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import java.io.IOException
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val logger = KotlinLogging.logger {}

class UserHandlerExceptionResolver: HandlerExceptionResolver {

    private val objectMapper = ObjectMapper()

    override fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any?,
        ex: Exception
    ): ModelAndView? {

        try {
            if (ex is UserException) {
                logger.info { "UserException resolver to 400" }
                val acceptHeader = request.getHeader("accept")
                response.status = HttpServletResponse.SC_BAD_REQUEST

                return if (MediaType.APPLICATION_JSON_VALUE == acceptHeader) {
                    val errorResult = mutableMapOf<String, Any?>()
                    errorResult["ex"] = ex.javaClass
                    errorResult["message"] = ex.message

                    val result = objectMapper.writeValueAsString(errorResult)

                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    response.characterEncoding = Charsets.UTF_8.name()
                    response.writer.write(result)
                    ModelAndView()
                } else {
                    // TEXT/ HTML
                    ModelAndView("error/500")
                }
            }

        } catch (e: IOException) {
            logger.error { "resolver ex $e" }
        }

        return null
    }
}
