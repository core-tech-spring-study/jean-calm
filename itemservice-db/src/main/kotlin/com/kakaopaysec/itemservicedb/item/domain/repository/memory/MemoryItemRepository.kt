package com.kakaopaysec.itemservicedb.item.domain.repository.memory

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.model.ItemRes
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto
import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import mu.KotlinLogging
import org.springframework.stereotype.Repository
import org.springframework.util.ObjectUtils
import javax.persistence.EntityNotFoundException

private val logger = KotlinLogging.logger {}


@Repository
class MemoryItemRepository : ItemRepository {

    override fun save(item: Item): Item {
        sequence = sequence.plus(1)
        item.id = sequence
        logger.debug { "id = ${item.id}" }
        store[item.id] = item
        return item
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        val findItem =
            findById(itemId) ?: kotlin.run { throw EntityNotFoundException("해당 상품이 존재하지 않습니다. itemId =  $itemId") }
        findItem.itemName = updateParam.itemName
        findItem.price = updateParam.price
        findItem.quantity = updateParam.quantity
    }

    override fun findById(id: Long): Item? {
        return store[id]
    }

    override fun findAll(cond: ItemSearchCond): List<ItemRes> {
        val itemName = cond.itemName
        val maxPrice = cond.maxPrice

        return store.values.filter {
            val isEmpty = ObjectUtils.isEmpty(itemName)
            var isContain = if (!isEmpty) {
                it.itemName.contains(itemName!!) ?: false
            } else {
                false
            }
            isEmpty || isContain
        }.filter {
            val isEmpty = maxPrice == null
            val isLstPrice = if (!isEmpty) {
                it.price <= maxPrice!!
            } else {
                false
            }
            isEmpty || isLstPrice
        }.map {
            val itemRes = ItemRes(it.id, it.itemName, it.price, it.quantity)
            itemRes
        }
    }

    fun clearStore() {
        store.clear()
    }

    companion object {
        private val store: MutableMap<Long, Item> = hashMapOf()
        private var sequence: Long = 0
    }
}
