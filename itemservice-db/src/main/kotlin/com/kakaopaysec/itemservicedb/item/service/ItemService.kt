package com.kakaopaysec.itemservicedb.item.service

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.model.ItemRes
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto

interface ItemService {
    fun save(item: Item): Item

    fun update(itemId: Long, updateParam: ItemUpdateDto)

    fun findById(id: Long): Item?

    fun findItems(cond: ItemSearchCond): List<ItemRes>
}
