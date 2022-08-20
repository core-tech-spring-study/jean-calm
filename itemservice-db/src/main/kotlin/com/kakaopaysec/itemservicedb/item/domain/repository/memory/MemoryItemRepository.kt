package com.kakaopaysec.itemservicedb.item.domain.repository.memory

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto
import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityNotFoundException

@Repository
class MemoryItemRepository: ItemRepository {

    override fun save(item: Item): Item {
        item.id = sequence.plus(1)
        store[item.id] = item
        return item
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        val findItem = findById(itemId)?: kotlin.run { throw EntityNotFoundException("해당 상품이 존재하지 않습니다. itemId =  $itemId") }
        findItem.itemName = updateParam.itemName
        findItem.price = updateParam.price
        findItem.quantity = updateParam.quantity
    }

    override fun findById(id: Long): Item? {
        return store[id]
    }

    override fun findAll(cond: ItemSearchCond): List<Item> {
        val itemName = cond.itemName
        val maxPrice = cond.maxPrice

        return store.values.filter {
            it.itemName?.contains(itemName) ?: false
        }.filter {
           it.price <= maxPrice
        }
    }

    fun clearStore() {
        store.clear()
    }

    companion object {
        private val store: MutableMap<Long, Item> = hashMapOf()
        private val sequence: Long = 0
    }
}
