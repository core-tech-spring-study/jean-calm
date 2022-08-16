package com.kakaopaysec.jdbc.member.domain.repository

import com.kakaopaysec.jdbc.connection.DBConnectionUtil
import com.kakaopaysec.jdbc.member.domain.Member
import mu.KotlinLogging
import org.springframework.jdbc.support.JdbcUtils
import java.sql.*
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}
/**
 * JDBC = DataSource 사용, JdbcUtils 사
 */

class MemberRepositoryV1(
    private val datasource: DataSource
) {

    fun save(member: Member): Member {
        val sql = "insert into member(member_id, money) values (?, ?)"
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null

        try {
            conn = getConnection()
            pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, member.memberId)
            pstmt.setInt(2, member.money)
            val count = pstmt.executeUpdate()
            logger.debug { "count = $count" }
            return member
        } catch (e: SQLException) {
            logger.error { "db error = $e" }
            throw e
        } finally {
            close(conn, pstmt, null)
        }
    }

    fun findById(memberId: String): Member {

        val sql = "select * from member where member_id = ?"
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null

        try {
            conn = DBConnectionUtil.getConnection()
            pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, memberId)
            rs = pstmt.executeQuery()

            if (rs.next()) {
                return Member(rs.getString("member_id"), rs.getInt("money"))
            } else {
                throw NoSuchElementException("member not found memberId = $memberId")
            }

        } catch (e: SQLException) {
            logger.error { "db error = $e" }
            throw e
        } finally {
            close(conn, pstmt, rs)
        }
    }

    private fun close(conn: Connection?, stmt: Statement?, rs: ResultSet?) {
        JdbcUtils.closeResultSet(rs)
        JdbcUtils.closeStatement(stmt)
        JdbcUtils.closeConnection(conn)
    }

    fun update(memberId: String, money: Int) {
        val sql = "update member set money = ? where member_id = ?"
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null

        try {
            conn = getConnection()
            pstmt = conn.prepareStatement(sql)
            pstmt.setInt(1, money)
            pstmt.setString(2, memberId)
            val count = pstmt.executeUpdate()
            logger.debug { "count = $count" }
        } catch (e: SQLException) {
            logger.error { "db error = $e" }
            throw e
        } finally {
            close(conn, pstmt, null)
        }
    }

    fun delete(memberId: String) {
        val sql = "delete from member where member_id = ?"
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null

        try {
            conn = getConnection()
            pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, memberId)
            val count = pstmt.executeUpdate()
            logger.debug { "count = $count" }
        } catch (e: SQLException) {
            logger.error { "db error = $e" }
            throw e
        } finally {
            close(conn, pstmt, null)
        }
    }

    private fun getConnection(): Connection {
        val con = datasource.connection
        logger.debug { "get connection = $con, class = ${con.javaClass}" }
        return con
    }
}

