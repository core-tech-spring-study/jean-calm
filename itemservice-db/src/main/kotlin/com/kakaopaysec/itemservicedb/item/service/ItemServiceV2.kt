package com.kakaopaysec.itemservicedb.item.service

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.model.ItemRes
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto
import com.kakaopaysec.itemservicedb.item.domain.repository.v2.ItemQueryRepositoryV2
import com.kakaopaysec.itemservicedb.item.domain.repository.v2.ItemRepositoryV2
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ItemServiceV2(
    private val itemRepositoryV2: ItemRepositoryV2,
    private val itemQueryRepositoryV2: ItemQueryRepositoryV2
): ItemService {

    override fun save(item: Item): Item {
        return itemRepositoryV2.save(item)
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        val findItem = itemRepositoryV2.findById(itemId)?.orElseThrow()
        findItem.itemName = updateParam.itemName
        findItem.price = updateParam.price
        findItem.quantity = updateParam.quantity
    }

    override fun findById(id: Long): Item? {
        return itemRepositoryV2.findById(id).orElseThrow()
    }

    override fun findItems(cond: ItemSearchCond): List<ItemRes> {
        return itemQueryRepositoryV2.findAll(cond)
    }
}
