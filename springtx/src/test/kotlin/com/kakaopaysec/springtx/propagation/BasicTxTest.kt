package com.kakaopaysec.springtx.propagation

import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.UnexpectedRollbackException
import org.springframework.transaction.interceptor.DefaultTransactionAttribute
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

@SpringBootTest
class BasicTxTest {

    @Autowired
    lateinit var txManager: PlatformTransactionManager

    @TestConfiguration
    class Config {

        @Bean
        fun transactionManager(dataSource: DataSource): PlatformTransactionManager {
            return DataSourceTransactionManager(dataSource)
        }
    }

    @Test
    fun commit() {
        logger.info { "트랜잭션 시작" }
        val status = txManager.getTransaction(DefaultTransactionAttribute())

        logger.info { "트랜잭션 커밋 시작" }
        txManager.commit(status)
        logger.info { "트랜잭션 커밋 완료" }
    }

    @Test
    fun rollback() {
        logger.info { "트랜잭션 시작" }
        val status = txManager.getTransaction(DefaultTransactionAttribute())

        logger.info { "트랜잭션 롤백 시작" }
        txManager.rollback(status)
        logger.info { "트랜잭션 롤백 완료" }
    }


    @Test
    fun double_commit() {
        logger.info { "트랜잭션1 시작" }
        val tx1 = txManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "트랜잭션1 커밋" }
        txManager.commit(tx1)

        logger.info { "트랜잭션2 시작" }
        val tx2 = txManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "트랜잭션2 커밋" }
        txManager.commit(tx2)
    }

    @Test
    fun double_commit_rollback() {
        logger.info { "트랜잭션1 시작" }
        val tx1 = txManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "트랜잭션1 커밋" }
        txManager.commit(tx1)

        logger.info { "트랜잭션2 시작" }
        val tx2 = txManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "트랜잭션2 롤백" }
        txManager.rollback(tx2)
    }

    @Test
    fun inner_commit() {
        logger.info { "외부 트랜잭션 시작" }
        val outer = txManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "outer.isNewTransaction = ${outer.isNewTransaction}" }

        inner()

        logger.info { "외부 트랜잭션 커밋" }
        txManager.commit(outer)
    }

    private inline fun inner() {
        logger.info { "내부 트랜잭션 시작" }
        val inner = txManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "inner.isNewTransaction = ${inner.isNewTransaction}" }
        logger.info { "내부 트랜잭션 커밋" }
        txManager.commit(inner)
    }

    @Test
    fun outer_rollback() {
        logger.info { "외부 트랜잭션 시작" }
        val outer = txManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "outer.isNewTransaction = ${outer.isNewTransaction}" }

        inner()

        logger.info { "외부 트랜잭션 롤백" }
        txManager.rollback(outer)
    }

    @Test
    fun inner_rollback() {
        logger.info { "외부 트랜잭션 시작" }
        val outer = txManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "outer.isNewTransaction = ${outer.isNewTransaction}" }

        logger.info { "내부 트랜잭션 시작" }
        val inner = txManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "inner.isNewTransaction = ${inner.isNewTransaction}" }
        logger.info { "내부 트랜잭션 롤백" }
        // TransactionManager에게 내부(논리) 트랜잭션이 roll back을 요청하면 트랜잭션 동기화 매니저에 rollbackOnly가 true로 설정 됨.
        txManager.rollback(inner)

        logger.info { "외부 트랜잭션 커밋" }

        // 신규 트랜잭션인 외부(논리) 트랜잭션은 커밋을 시도하면, 내부적으로 rollbackOnly 값을 확인 후 true이면 롤백합니다.
        assertThatThrownBy {
            txManager.commit(outer)
        }.isInstanceOf(UnexpectedRollbackException::class.java)
    }

    @Test
    fun inner_rollback_requires_new() {
        logger.info { "외부 트랜잭션 시작" }
        val outer = txManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "outer.isNewTransaction = ${outer.isNewTransaction}" }

        logger.info { "내부 트랜잭션 시작" }
        val definition = DefaultTransactionAttribute()
        definition.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW")

        val inner = txManager.getTransaction(definition)
        logger.info { "inner.isNewTransaction = ${inner.isNewTransaction}" }

        logger.info { "내부 트랜잭션 롤백" }
        txManager.rollback(inner)

        logger.info { "외부 트랜잭션 커밋" }
        txManager.commit(outer)
    }
}
