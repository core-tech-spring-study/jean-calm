package com.kakaopaysec.itemservicedb.item.adapter.`in`.web

import com.kakaopaysec.itemservicedb.item.domain.model.ItemDto
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto
import com.kakaopaysec.itemservicedb.item.service.ItemService
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.persistence.EntityNotFoundException

private val logger = KotlinLogging.logger {}

@RequestMapping("/items")
@Controller
class ItemWebController(
    private val itemService: ItemService
) {

    @GetMapping
    fun items(@ModelAttribute itemSearchCond: ItemSearchCond, model: Model): String {
        logger.debug { "ItemSearchCond = $itemSearchCond" }
        val items = itemService.findItems(itemSearchCond)
        items.forEach { logger.debug { "Item = ${it.itemName}" } }
        model.addAttribute("items", items)
        return "items"
    }

    @GetMapping("/{itemId}")
    fun item(@PathVariable itemId: Long, model: Model): String {
        logger.debug { "itemId: $itemId" }
        val item = itemService.findById(itemId)
        model.addAttribute("item", item)
        return "item"
    }

    @GetMapping("/addForm")
    fun addForm(): String {
        return "addForm"
    }

    @PostMapping("/add")
    fun addItem(@ModelAttribute itemDto: ItemDto, redirectAttributes: RedirectAttributes): String {
        logger.debug { "ItemDto = $itemDto" }
        val savedItem = itemService.save(itemDto.toEntity())
        redirectAttributes.addAttribute("itemId", savedItem.id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/items/{itemId}"
    }

    @GetMapping("/{itemId}/edit")
    fun editForm(@PathVariable itemId: Long, model: Model): String {
        logger.debug { "itemId: $itemId" }
        val item = itemService.findById(itemId) ?: kotlin.run { throw EntityNotFoundException("해당 상품이 존재하지 않습니다. itemId = $itemId") }
        model.addAttribute("item", itemId)
        return "editForm"
    }

    @PostMapping("/{itemId}/edit")
    fun edit(@PathVariable itemId: Long, @ModelAttribute updateParam: ItemUpdateDto): String {
        logger.debug { "itemId: $itemId, ItemUpdateDto: $updateParam" }
        itemService.update(itemId, updateParam)
        return "redirect:/items/{itemId}"
    }
}

