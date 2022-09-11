package com.kakaopaysec.upload.controller

import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest

private val logger = KotlinLogging.logger {}

@RequestMapping("/servlet/v1")
@Controller
class ServletUploadControllerV1 {

    @GetMapping("/upload")
    fun newFile(): String {
        return "upload-form"
    }

    @PostMapping("/upload")
    fun saveFileV1(request: HttpServletRequest): String {
        logger.info { "request = $request" }

        val itemName = request.getParameter("itemName")
        logger.info { "itemName = $itemName" }

        val parts = request.parts
        logger.info { "parts = $parts" }

        return "upload-from"
    }
}
