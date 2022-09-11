package com.kakaopaysec.upload.controller

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.util.StreamUtils
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest

private val logger = KotlinLogging.logger {}


@RequestMapping("/servlet/v2")
@Controller
class ServletUploadControllerV2 {

    @Value("\${file.dir}")
    lateinit var fileDir: String

    @GetMapping("/upload")
    fun newFile(): String {
        return "upload-form"
    }

    @PostMapping("/upload")
    fun saveFileV2(request: HttpServletRequest): String {
        logger.info { "request = $request" }

        val itemName = request.getParameter("itemName")
        logger.info { "itemName = $itemName" }

        val parts = request.parts
        logger.info { "parts = $parts" }

        for (part in parts) {
            logger.info { "==== PART ====" }
            logger.info { "name = ${part.name}" }
            val headerNames = part.headerNames
            headerNames.forEach {
                logger.info { "header = $it ${part.getHeader(it)} " }
            }
            // 편의 메서드
            logger.info { "submittedFileName = ${part.submittedFileName}" }
            logger.info { "size = ${part.size}" }

            // 데이터 읽기
            val inputStream = part.inputStream
            val body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8)
            logger.info { "body = $body" }

            // 파일에 저장하기
            if (StringUtils.hasText(part.submittedFileName)) {
                val fullPath = fileDir + part.submittedFileName
                logger.info { "파일 저장 fullPath = $fullPath" }
                part.write(fullPath)
            }
        }

        return "upload-from"
    }
}
