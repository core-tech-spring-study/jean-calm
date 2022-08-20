package com.kakaopaysec.itemservicedb.item.adapter.`in`.web

import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

private val logger = KotlinLogging.logger {}

@RequestMapping("/items")
@Controller
class ItemWebController {

    @GetMapping("/addForm")
    fun addForm(): String {
        return "addForm"
    }

    @GetMapping("/{itemId}/editForm")
    fun editForm(@PathVariable itemId: String, model: Model): String {
        logger.debug { "itemId: $itemId" }
        model.addAttribute("item", itemId)
        return "editForm"
    }
}

