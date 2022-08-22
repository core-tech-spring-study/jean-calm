package com.kakaopaysec.itemservicedb.item.domain.entity

import org.apache.ibatis.annotations.ConstructorArgs

class Item private constructor(
    var itemName: String,
    var price: Int,
    var quantity: Int
){

    var id: Long = 0

    companion object {
        fun createItem(_itemName: String, _price: Int, _quantity: Int): Item {
            return Item(_itemName, _price, _quantity)
        }
    }
}


