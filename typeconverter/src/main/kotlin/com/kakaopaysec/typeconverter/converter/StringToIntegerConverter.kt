package com.kakaopaysec.typeconverter.converter

import mu.KotlinLogging
import org.springframework.core.convert.converter.Converter

private val logger = KotlinLogging.logger {}

class StringToIntegerConverter: Converter<String, Int> {

    override fun convert(source: String): Int? {
        logger.info { "converter source = $source" }
        return source?.toInt()
    }
}
