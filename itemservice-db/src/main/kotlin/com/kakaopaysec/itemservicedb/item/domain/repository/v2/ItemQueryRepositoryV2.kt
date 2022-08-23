package com.kakaopaysec.itemservicedb.item.domain.repository.v2

import com.kakaopaysec.itemservicedb.item.domain.entity.QItem.item
import com.kakaopaysec.itemservicedb.item.domain.model.ItemRes
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import javax.persistence.EntityManager

class ItemQueryRepositoryV2(
    private val em: EntityManager
) {

    private val query = JPAQueryFactory(em)

    fun findAll(cond: ItemSearchCond): List<ItemRes> {
        val items = query.select(item)
            .from(item)
            .where(
                likeItemName(cond.itemName),
                maxPrice(cond.maxPrice)
            )
            .fetch()

        return items.map {
            val itemRes = ItemRes(it.id, it.itemName, it.price, it.quantity)
            itemRes
        }
    }

    private fun likeItemName(itemName: String?): BooleanExpression? {
        return itemName?.let {
            item.itemName.like("%$itemName%")
        }
    }

    private fun maxPrice(maxPrice: Int?): BooleanExpression? {
        return maxPrice?.let {
            item.price.loe(maxPrice)
        }
    }
}
