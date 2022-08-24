package com.kakaopaysec.springtx.exception

import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@SpringBootTest
class RollbackTest {

    @Autowired
    lateinit var service: RollbackService

    @Test
    fun runtimeException() {
        assertThatThrownBy {
            service.runtimeException()
        }.isInstanceOf(RuntimeException::class.java)
    }

    @Test
    fun checkedException() {
        // given
        assertThatThrownBy {
            service.checkedException()
        }.isInstanceOf(MyException::class.java)
    }

    @Test
    fun rollbackFor() {
        // given
        assertThatThrownBy {
            service.rollbackFor()
        }.isInstanceOf(MyException::class.java)
    }

    @TestConfiguration
    class RollbackTestConfig {
        @Bean
        fun rollbackService(): RollbackService {
            return RollbackService()
        }
    }

    open class RollbackService {

        // 런타임 예외 밸상: 롤백
        @Transactional
        open fun runtimeException() {
            logger.info { "call runtimeException" }
            throw RuntimeException()
        }

        // 체크 예외 발생: 커밋
        @Transactional
        open fun checkedException() {
            logger.info { "call checkedException" }
            throw MyException()
        }

        // 체크 예외 발생: 롤백
        @Transactional(rollbackFor = [MyException::class])
        open fun rollbackFor() {
            logger.info { "call rollbackFor" }
            throw MyException()
        }
    }

    class MyException: Exception()
}
