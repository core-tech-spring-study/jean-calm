package com.kakaopaysec.typeconverter.controller

import mu.KotlinLogging
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.NumberFormat
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

/**
 * 주의사항 - 메시지 컨버터(HttpMessageConverter)에는 컨버전 서비스가 적용되지 않습니다.
 * HttpMessageConverter의 역할은 메시지 바디의 내용을 객체로 변환하거나 객체를 HTTP 메시지 바디에 입력하는 것입니다.
 * 따라서 그 결과는 라이브러리를 사용하는 Jackson 같은 라이브러리에 달려 있습니다. 만약 JSON 결과로 만들어지는 숫자나 날짜 포맷을 변경하고 싶으면
 * 해당 라이브러리가 제공하는 설정을 통해서 포맷을 지정해야 합니다. 결론은 HttpMessageConverter는 컨버전 서비스와 전혀 관련이 없다는 것입니다.
 *
 * 컨버전 서비스는 @PathVariable, @RequestParam, @ModelAttribute 등에 적용 됩니다.
 */
@Controller
class FormatterController {

    @GetMapping("/formatter/edit")
    fun formatterForm(@ModelAttribute form: Form): String {
        logger.info { "form = $form" }
        return "item-view"
    }

}

data class Form(

    @NumberFormat(pattern = "###,###")
    val number: Int,

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val localDateTime: LocalDateTime
)
