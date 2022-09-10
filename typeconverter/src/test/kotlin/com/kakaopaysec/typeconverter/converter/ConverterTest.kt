package com.kakaopaysec.typeconverter.converter

import com.kakaopaysec.typeconverter.type.IpPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ConverterTest {

    @Test
    fun stringToInteger() {
        // given
        val converter = StringToIntegerConverter()

        // when
        val result = converter.convert("10")

        // then
        assertThat(result).isEqualTo(10)
    }

    @Test
    fun integerToString() {
        // given
        val converter = IntegerToStringConverter()

        // when
        val result = converter.convert(10)

        // then
        assertThat(result).isEqualTo("10")
    }

    @Test
    fun stringToIpPort() {
        // given
        val converter = StringToIpPortConverter()

        // when
        val result = converter.convert("localhost:8080")

        // then
        assertThat(result).isEqualTo(IpPort("localhost", 808))
    }

    @Test
    fun ipPortToString() {
        // given
        val converter = IpPortToStringConverter()

        // when
        val result = converter.convert(IpPort("localhost", 8080))

        // then
        assertThat(result).isEqualTo("localhost:8080")
    }

}
