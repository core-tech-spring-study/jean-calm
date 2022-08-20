package com.kakaopaysec.itemservicedb.item.domain.entity

class Item {
    var id: Long = 0
    var itemName: String? = null
    var price: Int = 0
    var quantity = 0

    private constructor(_itemName: String, _price: Int, _quantity: Int) {
        itemName = _itemName
        price = _price
        quantity = _quantity
    }

    companion object {
        fun createItem(_itemName: String, _price: Int, _quantity: Int): Item {
            return Item(_itemName, _price, _quantity)
        }
    }
}


