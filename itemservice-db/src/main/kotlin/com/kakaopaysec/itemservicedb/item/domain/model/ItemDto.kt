package com.kakaopaysec.itemservicedb.item.domain.model

data class ItemUpdateDto(
    val itemName: String,
    val price: Int,
    val quantity: Int
)

data class ItemSearchCond(
    val itemName: String,
    val maxPrice: Int
)
