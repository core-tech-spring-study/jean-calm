package com.kakaopaysec.exception.advice

import com.kakaopaysec.exception.exception.UserException
import com.kakaopaysec.exception.exhandler.ErrorResult
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger {}

@RestControllerAdvice(basePackages = ["com.kakaopaysec.exception.api"])
class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalExHandler(e: IllegalArgumentException): ErrorResult {
        logger.error { "[exceptionHandler] ex $e" }
        return ErrorResult("BAD", e.message)
    }

    @ExceptionHandler(UserException::class)
    fun userHandler(e: UserException): ResponseEntity<ErrorResult> {
        logger.error { "[UserExceptionHandler] $e" }
        return ResponseEntity(ErrorResult("BAD", "사용자 오류"), HttpStatus.BAD_REQUEST)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler // 메서드 파라미터의 예외타입 지정 시 @ExceptionHandler 어노테이션에서 엘리먼트 값 생략 가능함.
    fun exceptionHandler(e: Exception): ErrorResult {
        logger.error { "[ExceptionHandler] $e" }
        return ErrorResult("EX", "내부오류")
    }
}
