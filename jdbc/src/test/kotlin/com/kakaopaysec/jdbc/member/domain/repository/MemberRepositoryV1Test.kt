package com.kakaopaysec.jdbc.member.domain.repository

import com.kakaopaysec.jdbc.connection.password
import com.kakaopaysec.jdbc.connection.url
import com.kakaopaysec.jdbc.connection.userName
import com.kakaopaysec.jdbc.member.domain.Member
import com.zaxxer.hikari.HikariDataSource
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.jdbc.datasource.DriverManagerDataSource

private val logger = KotlinLogging.logger {}

internal class MemberRepositoryV1Test {

    lateinit var repository: MemberRepositoryV1

    @BeforeEach
    fun beforeEach() {
        // 기본 DriverManager - 항상 새로운 커넥션을 획득
        //val dataSource = DriverManagerDataSource(url, userName, password)
        val dataSource = HikariDataSource()
        dataSource.jdbcUrl = url
        dataSource.username = userName
        dataSource.password = password
        dataSource.poolName = "jeanPool"

        repository = MemberRepositoryV1(dataSource)
    }


    @Test
    fun crud() {
        // given
        val member = Member("memberV101", 20000)

        // when
        repository.save(member)

        val findMember = repository.findById(member.memberId)
        logger.debug { "findMember = $findMember" }

        // then
        Assertions.assertThat(findMember).isEqualTo(member)

        // update: money 10000 -> 20000
        repository.update(member.memberId, 30000)
        val updatedMember = repository.findById(member.memberId)

        Assertions.assertThat(updatedMember.money).isEqualTo(30000)

        // delete
        repository.delete(member.memberId)

        Assertions.assertThatThrownBy {
            repository.findById(member.memberId)
        }.isInstanceOf(java.util.NoSuchElementException::class.java)
    }
}
