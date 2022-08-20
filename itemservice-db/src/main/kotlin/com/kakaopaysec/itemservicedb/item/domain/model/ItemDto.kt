package com.kakaopaysec.itemservicedb.item.domain.model

import com.kakaopaysec.itemservicedb.item.domain.entity.Item

data class ItemDto(
    var itemName: String,
    var price: Int,
    var quantity: Int
) {
    fun toEntity(): Item {
        return Item.createItem(itemName, price, quantity)
    }
}

data class ItemRes(
    val id: Long,
    val itemName: String?,
    val price: Int,
    val quantity: Int
)


data class ItemUpdateDto(
    var itemName: String,
    var price: Int,
    var quantity: Int
)

data class ItemSearchCond(
    var itemName: String?,
    var maxPrice: Int?
)

