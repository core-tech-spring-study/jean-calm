package com.kakaopaysec.typeconverter.controller

import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class BindingExamController {

    @PostMapping("/type-check")
    fun typeCheck(@ModelAttribute item: Item, bindingResult: BindingResult): String {
        println("item = $item")

        if (bindingResult.hasErrors()) {
            val fieldError = bindingResult.fieldError
            println("fieldError = $fieldError")
        }

        return "OK"
    }
}

data class Item(
    val name: String?,
    val availableStock: Boolean?
)
