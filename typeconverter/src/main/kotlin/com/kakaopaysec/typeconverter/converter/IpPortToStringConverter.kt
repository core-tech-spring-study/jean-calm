package com.kakaopaysec.typeconverter.converter

import com.kakaopaysec.typeconverter.type.IpPort
import mu.KotlinLogging
import org.springframework.core.convert.converter.Converter

private val logger = KotlinLogging.logger {}

class IpPortToStringConverter: Converter<IpPort, String> {

    override fun convert(source: IpPort): String? {
        logger.info { "convert source = $source" }
        return String.format("%s:%s", source.ip, source.port.toString())
    }
}
