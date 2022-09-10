package com.kakaopaysec.typeconverter.converter

import com.kakaopaysec.typeconverter.type.IpPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.core.convert.support.DefaultConversionService

class ConversionServiceTest {

    @Test
    fun conversionService() {
        // given
        val conversionService = DefaultConversionService()

        // when
        conversionService.addConverter(StringToIntegerConverter())
        conversionService.addConverter(IntegerToStringConverter())
        conversionService.addConverter(StringToIpPortConverter())
        conversionService.addConverter(IpPortToStringConverter())

        // then
        assertThat(conversionService.convert("10", Int::class.java)).isEqualTo(10)
        assertThat(conversionService.convert(10, String::class.java)).isEqualTo("10")
        assertThat(conversionService.convert("localhost:8080", IpPort::class.java)).isEqualTo(IpPort("localhost", 8080))
        assertThat(conversionService.convert(IpPort("localhost", 8080), String::class.java)).isEqualTo("localhost:8080")
    }
}
