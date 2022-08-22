package com.kakaopaysec.itemservicedb.item.domain.repository.mybatis

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.model.ItemRes
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto
import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository

class MyBatisItemRepository(
    private val mapper: ItemMapper
): ItemRepository {

    override fun save(item: Item): Item {
        mapper.save(item)
        return item
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        mapper.update(itemId, updateParam)
    }

    override fun findById(id: Long): Item? {
        return mapper.findById(id)
    }

    override fun findAll(cond: ItemSearchCond): List<ItemRes> {

        val items = mapper.findAll(cond)

        val result = items.map {
            val itemRes = ItemRes(it.id, it.itemName, it.price, it.quantity)
            itemRes
        }

        return result
    }
}
