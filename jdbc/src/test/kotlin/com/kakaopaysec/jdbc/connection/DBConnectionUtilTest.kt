package com.kakaopaysec.jdbc.connection

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class DBConnectionUtilTest {

    @Test
    @DisplayName("Connection 객체를 가져온다.")
    fun connection() {
        val connection = DBConnectionUtil.getConnection()
        assertThat(connection).isNotNull
    }
}
