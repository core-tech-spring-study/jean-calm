package com.kakaopaysec.springtx.apply

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager
import javax.annotation.PostConstruct

private val logger = KotlinLogging.logger {}

@SpringBootTest
class InitTxTest {

    @Autowired
    lateinit var hello: Hello

    @Test
    fun go() {
        // 초기화 코드는 스프링이 초기화 시점에 호출한다.

    }


    @TestConfiguration
    class InitTxTestConfig {

        @Bean
        fun hello(): Hello {
            return Hello()
        }
    }


    open class Hello {

        // 스프링 컨테이너 기동 시점에 PostConstruct 초기화가 먼저 발생하기 때문에 트랜잭션이 적용되지 않는 점을 기억하자....
        @PostConstruct
        fun initV1() {
            val isActive = TransactionSynchronizationManager.isActualTransactionActive()
            logger.info { "Hello init @PostConstructor tx active = $isActive" }
        }

        // 스프링이 완전히 빈을 등록하고 기동되었을 때 동작함.
        @EventListener(ApplicationReadyEvent::class)
        @Transactional
        open fun initV2() {
            val isActive = TransactionSynchronizationManager.isActualTransactionActive()
            logger.info { "Hello init @PostConstructor tx active = $isActive" }
        }
    }
}
