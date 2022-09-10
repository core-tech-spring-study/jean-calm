package com.kakaopaysec.typeconverter.formatter

import com.kakaopaysec.typeconverter.converter.IpPortToStringConverter
import com.kakaopaysec.typeconverter.converter.StringToIpPortConverter
import com.kakaopaysec.typeconverter.type.IpPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.format.support.DefaultFormattingConversionService

internal class FormattingConversionServiceTest {

    @Test
    fun formattingConversionService() {
        // given
        val conversionService = DefaultFormattingConversionService()

        // 컨버터 등록
        conversionService.addConverter(StringToIpPortConverter())
        conversionService.addConverter(IpPortToStringConverter())

        // 포맷터 등록
        conversionService.addFormatter(MyNumberFormatter())

        // then
        assertThat(conversionService.convert("localhost:8080", IpPort::class.java)).isEqualTo(IpPort("localhost", 8080))

        // 포맷터 사용
        assertThat(conversionService.convert(1000, String::class.java)).isEqualTo("1,000")
        assertThat(conversionService.convert("1,000", Long::class.java)).isEqualTo(1000)
    }
}
