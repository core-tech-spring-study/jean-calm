package com.kakaopaysec.advanced.log.trace.threadlocal

import com.kakaopaysec.advanced.log.trace.threadlocal.code.ThreadLocalService
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

class ThreadLocalTest {

    private val threadLocalService = ThreadLocalService()

    @Test
    fun field() {
        // given
        logger.info { "main start" }

        val userA = Runnable {
            threadLocalService.logic("userA")
        }

        val userB = Runnable {
            threadLocalService.logic("userB")
        }

        val threadA = Thread(userA)
        threadA.name = "thread-A"

        val threadB = Thread(userB)
        threadB.name = "thread-B"

        // when
        threadA.start()
        TimeUnit.MILLISECONDS.sleep(100)

        threadB.start()
        threadB.join()

        println("main exit")
    }
}
