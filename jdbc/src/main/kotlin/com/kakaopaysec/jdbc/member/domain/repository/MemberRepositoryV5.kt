package com.kakaopaysec.jdbc.member.domain.repository

import com.kakaopaysec.jdbc.member.domain.Member
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.datasource.DataSourceUtils
import org.springframework.jdbc.support.JdbcUtils
import java.sql.*
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}


/**
 * JdbcTemplate 예제
 *
 */
class MemberRepositoryV5(
    private val datasource: DataSource
) {

    lateinit var jdbcTemplate: JdbcTemplate

    init {
        jdbcTemplate = JdbcTemplate(datasource)
    }

     fun save(member: Member): Member {
        val sql = "insert into member(member_id, money) values (?, ?)"
         jdbcTemplate.update(sql, member.memberId, member.money)
         return member
    }

    fun findById(memberId: String): Member? {
        val sql = "select * from member where member_id = ?"
        return jdbcTemplate.queryForObject(sql, memberRowMapper(), memberId)
    }

    private fun memberRowMapper(): RowMapper<Member> {
        val mapper = RowMapper<Member> { resultSet, rowNum ->
            Member(
                resultSet.getString("member_id"),
                resultSet.getInt("money"),
            )
        }

        return mapper
    }

    fun update(memberId: String, money: Int) {
        val sql = "update member set money = ? where member_id = ?"
        jdbcTemplate.update(sql, money, memberId)
    }

     fun delete(memberId: String) {
        val sql = "delete from member where member_id = ?"
        jdbcTemplate.update(sql, memberId)
    }


    private fun getConnection(): Connection {
        // 주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 합니다.
        val con = DataSourceUtils.getConnection(datasource)
        logger.debug { "get connection = $con, class = ${con.javaClass}" }
        return con
    }

    private fun close(conn: Connection?, stmt: Statement?, rs: ResultSet?) {
        JdbcUtils.closeResultSet(rs)
        JdbcUtils.closeStatement(stmt)
        // 주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 합니다.
        DataSourceUtils.releaseConnection(conn, datasource)
    }
}
