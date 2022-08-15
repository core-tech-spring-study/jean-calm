package com.kakaopaysec.jdbc.connection

import mu.KotlinLogging
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

private val logger = KotlinLogging.logger {}

class DBConnectionUtil {
    companion object {
        fun getConnection(): Connection {
            try {
                val connection = DriverManager.getConnection(url, userName, password)
                logger.debug { "get connection = $connection, class = ${connection.javaClass}" }
                return connection
            } catch (e: SQLException) {
                throw IllegalStateException(e)
            }
        }
    }
}
