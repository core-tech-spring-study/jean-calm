package com.kakaopaysec.itemservicedb.item.domain.entity

import javax.persistence.*

@Entity
class Item private constructor(
    @Column(name = "item_name", length = 10)
    var itemName: String,
    var price: Int,
    var quantity: Int
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    companion object {
        fun createItem(_itemName: String, _price: Int, _quantity: Int): Item {
            return Item(_itemName, _price, _quantity)
        }
    }
}


