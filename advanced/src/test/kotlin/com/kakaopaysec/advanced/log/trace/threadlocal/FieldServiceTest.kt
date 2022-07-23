package com.kakaopaysec.advanced.log.trace.threadlocal

import com.kakaopaysec.advanced.log.trace.threadlocal.code.FieldService
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

class FieldServiceTest {

    private val fieldService = FieldService()

    @Test
    fun field() {
        // given
        logger.info { "main start" }

        val userA = Runnable {
            fieldService.logic("userA")
        }

        val userB = Runnable {
            fieldService.logic("userB")
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
