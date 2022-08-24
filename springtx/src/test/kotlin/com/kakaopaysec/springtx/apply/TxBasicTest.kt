package com.kakaopaysec.springtx.apply

import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.aop.support.AopUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

private val logger = KotlinLogging.logger {}

@SpringBootTest
class TxBasicTest {

    @Autowired
    lateinit var basicService: BasicService

    @Test
    fun proxyCheck() {
        logger.info { "aop class = ${basicService.javaClass}" }
        assertThat(AopUtils.isAopProxy(basicService)).isTrue
    }

    @Test
    fun txTest() {
        basicService.tx()
        basicService.nonTx()
    }

    @TestConfiguration
    class TxApplyBasicConfig {

        @Bean
        fun basicService(): BasicService {
            return BasicService()
        }
    }


    open class BasicService {

        @Transactional
        open fun tx() {
            logger.info { "call tx" }
            val txActive = TransactionSynchronizationManager.isActualTransactionActive()
            logger.info { "tx active = $txActive" }
        }

        fun nonTx() {
            logger.info { "call nonTx" }
            val txActive = TransactionSynchronizationManager.isActualTransactionActive()
            logger.info { "tx active = $txActive" }
        }
    }

}
