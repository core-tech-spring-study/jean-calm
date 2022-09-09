package com.kakaopaysec.itemservicedb

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Transactional
class TestDataInit(
    private val itemRepository: ItemRepository
) {
    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent::class)
    fun initData() {
        logger.debug { "test data init" }
        itemRepository.save(Item.createItem("itemA", 10000, 10))
        itemRepository.save(Item.createItem("itemB", 20000, 20))
    }
}
