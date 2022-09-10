package com.kakaopaysec.exception.servlet

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val logger = KotlinLogging.logger {}

const val ERROR_EXCEPTION = "javax.servlet.error.exception"
const val ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type"
const val ERROR_MESSAGE = "javax.servlet.error.message"
const val ERROR_REQUEST_URI = "javax.servlet.error.request_uri"
const val ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name"
const val ERROR_STATUS_CODE = "javax.servlet.error.status_code"

@Controller
class ErrorPageController {

    @RequestMapping("/error-page/404")
    fun errorPage404(request: HttpServletRequest, response: HttpServletResponse): String {
        logger.error { "errorPage 404" }
        printErrorInfo(request)
        return "error-page/404"
    }

    @RequestMapping("/error-page/500")
    fun errorPage500(request: HttpServletRequest, response: HttpServletResponse): String {
        logger.error { "errorPage 500" }
        printErrorInfo(request)
        return "error-page/500"
    }

    @RequestMapping("/error-page/500", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun errorPage500Api(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Map<String, Any?>> {
        logger.error { "Api errorPage 500" }

        val result = mutableMapOf<String, Any?>()
        val ex = request.getAttribute(ERROR_EXCEPTION) as Exception
        result["status"] = request.getAttribute(ERROR_STATUS_CODE)
        result["message"] = ex.message

        val statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as Int

        return ResponseEntity(result, HttpStatus.valueOf(statusCode))
    }

    fun printErrorInfo(request: HttpServletRequest) {
        logger.info {"ERROR_EXCEPTION: ${request.getAttribute(ERROR_EXCEPTION)}"  }
        logger.info {"ERROR_EXCEPTION_TYPE: ${request.getAttribute(ERROR_EXCEPTION_TYPE)}"  }
        logger.info {"ERROR_MESSAGE: ${request.getAttribute(ERROR_MESSAGE)}"  }
        logger.info {"ERROR_REQUEST_URI: ${request.getAttribute(ERROR_REQUEST_URI)}"  }
        logger.info {"ERROR_SERVLET_NAME: ${request.getAttribute(ERROR_SERVLET_NAME)}"  }
        logger.info {"ERROR_STATUS_CODE: ${request.getAttribute(ERROR_STATUS_CODE)}"  }
        logger.info { "dispatcherType = ${request.dispatcherType}" }
    }
}
