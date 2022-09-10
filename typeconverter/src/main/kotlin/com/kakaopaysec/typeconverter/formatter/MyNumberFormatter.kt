package com.kakaopaysec.typeconverter.formatter

import mu.KotlinLogging
import org.springframework.format.Formatter
import java.text.NumberFormat
import java.util.*

private val logger = KotlinLogging.logger {}

class MyNumberFormatter: Formatter<Number> {
    override fun print(`object`: Number, locale: Locale): String {
        logger.info { "object = $`object`, locale = $locale" }
        val instance = NumberFormat.getInstance(locale)
        return instance.format(`object`)
    }

    override fun parse(text: String, locale: Locale): Number {
        logger.info { "text = $text, locale = $locale" }
        // "1,000" -> 1000
        val format = NumberFormat.getInstance(locale)
        return format.parse(text)
    }
}
