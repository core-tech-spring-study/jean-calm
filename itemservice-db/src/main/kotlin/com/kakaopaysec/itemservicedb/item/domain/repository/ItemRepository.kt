package com.kakaopaysec.itemservicedb.item.domain.repository

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.model.ItemRes
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto

interface ItemRepository {
    fun save(item: Item): Item

    fun update(itemId: Long, updateParam: ItemUpdateDto)

    fun findById(id: Long): Item?

    fun findAll(cond: ItemSearchCond): List<ItemRes>
}
