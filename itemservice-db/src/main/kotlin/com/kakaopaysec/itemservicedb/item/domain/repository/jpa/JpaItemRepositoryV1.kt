package com.kakaopaysec.itemservicedb.item.domain.repository.jpa

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.model.ItemRes
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto
import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import mu.KotlinLogging
import org.springframework.util.StringUtils
import javax.persistence.EntityManager
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {}

class JpaItemRepositoryV1(
    private val em: EntityManager
): ItemRepository {

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

        var jpql = "select i from Item i"

        val maxPrice = cond.maxPrice
        val itemName = cond.itemName

        if (StringUtils.hasText(itemName) || maxPrice != null) {
            jpql += " where"
        }

        var andFlag = false

        var param = arrayListOf<Any>()

        if (StringUtils.hasText(itemName)) {
            jpql += " i.itemName like concat('%',:itemName,'%')"
            param.add(itemName!!)
            andFlag = true
        }

        if (maxPrice != null) {
            if (andFlag) {
                jpql += "and"
            }
            jpql += " i.price <= :maxPrice"
            param.add(maxPrice!!)
        }

        logger.debug { "jpql = $jpql" }

        var query: TypedQuery<Item> = em.createQuery(jpql, Item::class.java)

        if (StringUtils.hasText(itemName)) {
            query.setParameter("itemName"!!, itemName)
        }

        if (maxPrice != null) {
            query.setParameter("maxPrice"!!, maxPrice)
        }

        val resultList = query.resultList.toList()

        return resultList.map {
            val itemRes = ItemRes(it.id, it.itemName, it.price, it.quantity)
            itemRes
        }
    }
}
