package com.kakaopaysec.exception.api

import com.kakaopaysec.exception.exception.UserException
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
class ApiExceptionV3Controller {

    @GetMapping("/api3/members/{id}")
    fun getMember(@PathVariable id: String): MemberDto {

        if (id == "ex") {
            throw RuntimeException("잘못된 사용자")
        }

        if (id == "bad") {
            throw IllegalArgumentException("잘못된 입력 값")
        }

        if (id == "user-ex") {
            throw UserException("사용자 오류")
        }

        return MemberDto(id, "hello $id")
    }

    data class MemberDto(
        val memberId: String,
        val name: String
    )
}
