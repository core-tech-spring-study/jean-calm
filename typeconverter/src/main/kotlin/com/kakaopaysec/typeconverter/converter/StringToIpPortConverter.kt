package com.kakaopaysec.typeconverter.converter

import com.kakaopaysec.typeconverter.type.IpPort
import mu.KotlinLogging
import org.springframework.core.convert.converter.Converter

private val logger = KotlinLogging.logger {}

class StringToIpPortConverter : Converter<String, IpPort> {
    override fun convert(source: String): IpPort? {
        logger.info { "convert source = $source" }
        return IpPort(source.split(":")[0], source.split(":")[1].toInt())
    }
}
