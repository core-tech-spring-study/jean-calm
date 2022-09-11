package com.kakaopaysec.upload.controller

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.util.StreamUtils
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest

private val logger = KotlinLogging.logger {}

@RequestMapping("/spring")
@Controller
class SpringUploadController {

    @Value("\${file.dir}")
    lateinit var fileDir: String

    @GetMapping("/upload")
    fun newFile(): String {
        return "upload-form"
    }

    @PostMapping("/upload")
    fun saveFile(@RequestParam itemName: String,
                 @RequestParam file: MultipartFile,
                 request: HttpServletRequest): String {

        logger.info { "request = $request" }
        logger.info { "itemName = $itemName" }
        logger.info { "multipartFile = $file" }

        if (!file.isEmpty) {
            val fullPath = fileDir + file.originalFilename
            logger.info { "파일 저장 fullPath = $fullPath" }
            file.transferTo(File(fullPath))
        }

        return "upload-from"
    }
}
