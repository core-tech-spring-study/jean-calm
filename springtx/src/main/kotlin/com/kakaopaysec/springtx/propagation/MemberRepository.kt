package com.kakaopaysec.springtx.propagation

import mu.KotlinLogging
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

private val logger = KotlinLogging.logger {}

@Repository
class MemberRepository(
    private val em: EntityManager
) {

    @Transactional
    fun save(member: Member) {
        logger.info { "member 저장" }
        em.persist(member)
    }

    @Transactional
    fun find(username: String): Optional<Member> {
        return em.createQuery("select m from Member m where m.username = :username", Member::class.java)
            .setParameter("username", username)
            .resultList
            .stream()
            .findAny()
    }
}
