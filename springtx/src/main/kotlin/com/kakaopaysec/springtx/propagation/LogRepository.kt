package com.kakaopaysec.springtx.propagation

import mu.KotlinLogging
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

private val logger = KotlinLogging.logger {}

@Repository
class LogRepository(
    private val em: EntityManager
) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun save(logMessage: Log) {
        logger.info { "log 저장" }
        em.persist(logMessage)

        if (logMessage.message.contains("로그예외")) {
            logger.info { "log 저장시 예외 발생" }
            throw RuntimeException("예외 발생")
        }
    }

    @Transactional
    fun find(message: String): Optional<Log> {
        return em.createQuery("select l from Log l where l.message = :message", Log::class.java)
            .setParameter("message", message)
            .resultList
            .stream()
            .findAny()
    }
}
