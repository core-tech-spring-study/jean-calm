package com.kakaopaysec.jdbc.connection

import com.zaxxer.hikari.HikariDataSource
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.sql.DriverManager
import java.util.concurrent.TimeUnit
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

    /**
     * 커넥션 풀은 어플리케이션 실행에 영향을 주지 않기 위해 별도의 스레드에서 커넥션들을 생성함.
     */
    @Test
    fun dataSourceConnectionPool() {
        val datasource = HikariDataSource()
        datasource.jdbcUrl = url
        datasource.username = userName
        datasource.password = password
        datasource.maximumPoolSize = 10
        datasource.poolName = "jeanPool"
        userDataSource(datasource)
        TimeUnit.SECONDS.sleep(1)
    }

    private fun userDataSource(datasource: DataSource) {
        val con1 = datasource.connection
        val con2 = datasource.connection
        logger.debug { "get connection = $con1, class = ${con1.javaClass}" }
        logger.debug { "get connection = $con2, class = ${con2.javaClass}" }
    }
}
