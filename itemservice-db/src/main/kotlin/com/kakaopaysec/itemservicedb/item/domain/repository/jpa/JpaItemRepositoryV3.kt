package com.kakaopaysec.itemservicedb.item.domain.repository.jpa

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.entity.QItem
import com.kakaopaysec.itemservicedb.item.domain.model.ItemRes
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto
import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import mu.KotlinLogging
import org.springframework.util.StringUtils
import javax.persistence.EntityManager

private val logger = KotlinLogging.logger {}

class JpaItemRepositoryV3(
    private val em: EntityManager,
) : ItemRepository {

    var query: JPAQueryFactory = JPAQueryFactory(em)

    override fun save(item: Item): Item {
        em.persist(item)
        return item
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        val findItem = em.find(Item::class.java, itemId)
        findItem.itemName = updateParam.itemName
        findItem.price = updateParam.price
        findItem.quantity = updateParam.quantity
    }

    override fun findById(id: Long): Item? {
        return em.find(Item::class.java, id)
    }

    override fun findAll(cond: ItemSearchCond): List<ItemRes> {
        val maxPrice = cond.maxPrice
        val itemName = cond.itemName

        val item = QItem.item
        val builder = BooleanBuilder()

        if (StringUtils.hasText(itemName)) {
            builder.and(item.itemName.like("%$itemName%"))
        }

        if (maxPrice != null) {
            builder.and(item.price.loe(maxPrice))
        }

        val result = query.select(item)
            .from(item)
            .where(builder)
            .fetch()

        return result.map {
            val itemRes = ItemRes(it.id, it.itemName, it.price, it.quantity)
            itemRes
        }
    }
}
