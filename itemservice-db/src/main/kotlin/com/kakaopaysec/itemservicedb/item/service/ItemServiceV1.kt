package com.kakaopaysec.itemservicedb.item.service

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto
import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository

class ItemServiceV1(
    private val itemRepository: ItemRepository
): ItemService {

    override fun save(item: Item): Item {
        return itemRepository.save(item)
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        itemRepository.update(itemId, updateParam)
    }

    override fun findById(id: Long): Item? {
        return itemRepository.findById(id)
    }

    override fun findAll(cond: ItemSearchCond): List<Item> {
        return itemRepository.findAll(cond)
    }
}
