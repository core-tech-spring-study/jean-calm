package com.kakaopaysec.springtx.apply

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

private val logger = KotlinLogging.logger {}

@SpringBootTest
class TxLevelTest {

    @Autowired
    lateinit var service: LevelService

    @Test
    fun orderTest() {
        service.write()
        service.read()
    }


    @TestConfiguration
    class TxLevelTestConfig {

        @Bean
        fun levelService(): LevelService {
            return LevelService()
        }
    }


    @Transactional(readOnly = true)
    class LevelService {

        @Transactional(readOnly = false)
        fun write() {
            logger.info { "call write" }
            printTxInfo()
        }

        fun read() {
            logger.info { "call read" }
            printTxInfo()
        }

        private fun printTxInfo() {
            val txActive = TransactionSynchronizationManager.isActualTransactionActive()
            logger.info { "txActive = $txActive" }
            val readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            logger.info { "tx readOnly = $readOnly" }
        }
    }
}
