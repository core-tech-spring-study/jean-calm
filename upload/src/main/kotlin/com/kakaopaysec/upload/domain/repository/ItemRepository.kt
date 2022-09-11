package com.kakaopaysec.upload.domain.repository

import com.kakaopaysec.upload.domain.Item
import org.springframework.stereotype.Repository

@Repository
class ItemRepository {

    val store = mutableMapOf<Long, Item>()
    var sequence: Long = 0

    fun save(item: Item): Item {
        sequence = sequence.plus(1)
        item.id = sequence
        store[item.id!!] = item
        return item
    }

    fun findById(id: Long): Item? {
        return store[id]
    }
}
