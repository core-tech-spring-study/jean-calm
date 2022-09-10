package com.kakaopaysec.typeconverter.converter

import mu.KotlinLogging
import org.springframework.core.convert.converter.Converter

private val logger = KotlinLogging.logger {}

class IntegerToStringConverter: Converter<Int, String> {

    override fun convert(source: Int): String? {
        logger.info { "converter source = $source" }
        return source.toString()
    }
}
