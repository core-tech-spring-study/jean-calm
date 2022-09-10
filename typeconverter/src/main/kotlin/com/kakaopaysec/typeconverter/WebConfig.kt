package com.kakaopaysec.typeconverter

import com.kakaopaysec.typeconverter.converter.IntegerToStringConverter
import com.kakaopaysec.typeconverter.converter.IpPortToStringConverter
import com.kakaopaysec.typeconverter.converter.StringToIntegerConverter
import com.kakaopaysec.typeconverter.converter.StringToIpPortConverter
import com.kakaopaysec.typeconverter.formatter.MyNumberFormatter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        // 주석처리 우선순위 컨버터 > 포맷터
        //registry.addConverter(StringToIntegerConverter())
        //registry.addConverter(IntegerToStringConverter())
        registry.addConverter(StringToIpPortConverter())
        registry.addConverter(IpPortToStringConverter())
        registry.addFormatter(MyNumberFormatter())
    }
}
