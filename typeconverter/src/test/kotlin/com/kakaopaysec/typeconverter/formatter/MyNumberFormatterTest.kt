package com.kakaopaysec.typeconverter.formatter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class MyNumberFormatterTest {

    private val formatter = MyNumberFormatter()

    @Test
    fun parse() {
        // when
        val result = formatter.parse("1,000", Locale.KOREA)

        // then
        assertThat(result).isEqualTo(1000L)
    }

    @Test
    fun print() {
        // given
        val number: Number = 1000L

        // when
        val result = formatter.print(number, Locale.KOREA)

        // then
        assertThat(result).isEqualTo("1,000")
    }
}
