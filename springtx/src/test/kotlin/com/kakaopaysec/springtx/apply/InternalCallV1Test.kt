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
class InternalCallV1Test {

    @Autowired
    lateinit var callService: CallService

    @Test
    fun printProxy() {
        logger.info { "callService class = ${callService::class.java}" }
    }

    @Test
    fun externalCall() {
        callService.external()
    }

    @TestConfiguration
    class InternalCallV1TestConfig {

        @Bean
        fun callService(): CallService {
            return CallService(internalService())
        }

        @Bean
        fun internalService(): InternalService {
            logger.debug { "internalService Bean init" }
            return InternalService()
        }
    }

    open class CallService(private val internalService: InternalService) {

        fun external() {
            logger.info { "call external" }
            printTxInfo()
            internalService.internal()
        }

        private fun printTxInfo() {
            val txActive = TransactionSynchronizationManager.isActualTransactionActive()
            logger.info { "txActive = $txActive" }
            val readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            logger.info { "tx readOnly = $readOnly" }
        }
    }

    open class InternalService {

        @Transactional
        open fun internal() {
            logger.info { "call internal" }
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
