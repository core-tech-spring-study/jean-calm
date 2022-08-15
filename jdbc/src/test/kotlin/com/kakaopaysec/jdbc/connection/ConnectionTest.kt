package com.kakaopaysec.jdbc.connection

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.sql.DriverManager
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

class ConnectionTest {

    @Test
    fun driverManager() {
        val con1 = DriverManager.getConnection(url, userName, password)
        val con2 = DriverManager.getConnection(url, userName, password)
        logger.debug { "get connection = $con1, class = ${con1.javaClass}" }
        logger.debug { "get connection = $con2, class = ${con2.javaClass}" }
    }

    @Test
    fun dataSourceDriverManager() {
        //DriverManagerDataSource - 항상 새로운 커넥션을 획득
        val datasource = DriverManagerDataSource(url, userName, password)
        userDataSource(datasource)
    }

    private fun userDataSource(datasource: DataSource) {
        val con1 = datasource.connection
        val con2 = datasource.connection
        logger.debug { "get connection = $con1, class = ${con1.javaClass}" }
        logger.debug { "get connection = $con2, class = ${con2.javaClass}" }
    }
}
