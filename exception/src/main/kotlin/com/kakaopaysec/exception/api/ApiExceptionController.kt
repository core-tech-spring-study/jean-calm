package com.kakaopaysec.exception.api

import com.kakaopaysec.exception.exception.BadRequestException
import com.kakaopaysec.exception.exception.UserException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class ApiExceptionController {

    @GetMapping("/api/members/{id}")
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

    @GetMapping("/api/response-status-ex1")
    fun responseStatusEx1() {
        throw BadRequestException()
    }

    @GetMapping("/api/response-status-ex2")
    fun responseStatusEx2() {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", IllegalArgumentException())
    }


    @GetMapping("/api/default-handler-ex")
    fun defaultException(@RequestParam data: Int): String {
        return "ok"
    }

    data class MemberDto(
        val memberId: String,
        val name: String
    )
}
