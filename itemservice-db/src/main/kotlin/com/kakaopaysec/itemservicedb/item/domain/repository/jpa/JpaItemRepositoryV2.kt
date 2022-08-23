package com.kakaopaysec.itemservicedb.item.domain.repository.jpa

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.model.ItemRes
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto
import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import mu.KotlinLogging
import org.springframework.util.StringUtils

private val logger = KotlinLogging.logger {}

class JpaItemRepositoryV2(
    private val repository: SpringDataJpaRepository
) : ItemRepository {

    override fun save(item: Item): Item {
        return repository.save(item)
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        val findItem = repository.findById(itemId)?.orElseThrow()
        findItem.itemName = updateParam.itemName
        findItem.price = updateParam.price
        findItem.quantity = updateParam.quantity
    }

    override fun findById(id: Long): Item? {
        return repository.findById(id)?.orElseThrow()
    }

    override fun findAll(cond: ItemSearchCond): List<ItemRes> {
        val maxPrice = cond.maxPrice
        val itemName = cond.itemName

        if (StringUtils.hasText(itemName) && maxPrice != null) {
            val findItems = repository.findItems("%$itemName%", maxPrice)
            return findItems.map {
                val itemRes = ItemRes(it.id, it.itemName, it.price, it.quantity)
                itemRes
            }
        } else if (StringUtils.hasText(itemName)) {
            val findItems = repository.findByItemNameLike("%$itemName%")
            return findItems.map {
                val itemRes = ItemRes(it.id, it.itemName, it.price, it.quantity)
                itemRes
            }
        } else if (maxPrice != null) {
            val findItems = repository.findByPriceLessThanEqual(maxPrice)
            return findItems.map {
                val itemRes = ItemRes(it.id, it.itemName, it.price, it.quantity)
                itemRes
            }
        }

        return repository.findAll().map {
            val itemRes = ItemRes(it.id, it.itemName, it.price, it.quantity)
            itemRes
        }
    }
}
