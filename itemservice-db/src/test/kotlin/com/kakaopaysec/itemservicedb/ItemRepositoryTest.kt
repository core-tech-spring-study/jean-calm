package com.kakaopaysec.itemservicedb

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.model.ItemRes
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto
import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import com.kakaopaysec.itemservicedb.item.domain.repository.memory.MemoryItemRepository
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

private val logger = KotlinLogging.logger {}

@Transactional
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    lateinit var itemRepository: ItemRepository


    @AfterEach
    fun afterEach() {
        if (itemRepository is MemoryItemRepository) {
            (itemRepository as MemoryItemRepository).clearStore()
        }
    }

    @Test
    fun save() {
        // given
        val item = Item.createItem("ItemA", 10000, 10)

        // when
        val savedItem = itemRepository.save(item)

        // then
        val findItem = itemRepository.findById(item.id)
        assertThat(findItem).isEqualTo(savedItem)
    }

    @Test
    fun updateItem() {
        // given
        val item = Item.createItem("item1", 10000, 10)
        val savedItem = itemRepository.save(item)
        val itemId = savedItem.id

        // when
        val updateParam = ItemUpdateDto("item2", 20000, 30)
        itemRepository.update(itemId, updateParam)

        // then
        val findItem = itemRepository.findById(itemId)
            ?: kotlin.run { throw EntityNotFoundException("해당 상품이 존재하지 않습니다. itemId = $itemId") }
        assertThat(findItem.itemName).isEqualTo(updateParam.itemName)
        assertThat(findItem.price).isEqualTo(updateParam.price)
        assertThat(findItem.quantity).isEqualTo(updateParam.quantity)
    }

    @Test
    fun findItems() {
        // given
        val item1 = Item.createItem("itemA-1", 10000, 10)
        val item2 = Item.createItem("itemA-2", 20000, 20)
        val item3 = Item.createItem("itemB-1", 30000, 30)

        logger.debug { "repository = ${itemRepository::class.java}" }
        itemRepository.save(item1)
        itemRepository.save(item2)
        itemRepository.save(item3)

        // when
        val findItem1 = itemRepository.findById(item1.id)!!
        val findItem2 = itemRepository.findById(item2.id)!!
        val findItem3 = itemRepository.findById(item3.id)!!

        val itemRes1 = ItemRes(findItem1.id, findItem1.itemName, findItem1.price, findItem1.quantity)
        val itemRes2 = ItemRes(findItem2.id, findItem2.itemName, findItem2.price, findItem2.quantity)
        val itemRes3 = ItemRes(findItem3.id, findItem3.itemName, findItem3.price, findItem3.quantity)

        // then
        // 둘다 없음 검증
        test(null, null, itemRes1, itemRes2, itemRes3)
        test("", null, itemRes1, itemRes2, itemRes3)

        // itemName 검증
        test("itemA", null, itemRes1, itemRes2)
        test("temA", null, itemRes1, itemRes2)
        test("itemB", null, itemRes3)

        // maxPrice 검증
        test(null, 10000, itemRes1)

        // 둘다 있음 검증
        test("itemA", 10000, itemRes1)
    }

    private inline fun test(itemName: String?, maxPrice: Int?, vararg items: ItemRes) {
        val result = itemRepository.findAll(ItemSearchCond(itemName, maxPrice))
        result.forEach { logger.debug { "itemRes = $it" } }
        assertThat(result).containsExactly(*items)
    }
}
