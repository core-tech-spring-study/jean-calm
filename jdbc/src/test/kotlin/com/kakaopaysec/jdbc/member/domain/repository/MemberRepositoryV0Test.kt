package com.kakaopaysec.jdbc.member.domain.repository

import com.kakaopaysec.jdbc.member.domain.Member
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

private val logger = KotlinLogging.logger {}

internal class MemberRepositoryV0Test {

    private val repository = MemberRepositoryV0()

    @Test
    fun crud() {
        // given
        val member = Member("memberV101", 20000)

        // when
        repository.save(member)

        val findMember = repository.findById(member.memberId)
        logger.debug { "findMember = $findMember" }

        // then
        assertThat(findMember).isEqualTo(member)

        // update: money 10000 -> 20000
        repository.update(member.memberId, 30000)
        val updatedMember = repository.findById(member.memberId)

        assertThat(updatedMember.money).isEqualTo(30000)

        // delete
        repository.delete(member.memberId)

        assertThatThrownBy {
            repository.findById(member.memberId)
        }.isInstanceOf(java.util.NoSuchElementException::class.java)
    }
}


