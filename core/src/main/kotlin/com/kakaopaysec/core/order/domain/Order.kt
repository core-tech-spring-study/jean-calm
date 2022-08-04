package com.kakaopaysec.core.order.domain

class Order(
    var memberId: Long,
    var itemName: String,
    var itemPrice: Int,
    var discountPrice: Int,

) {

    fun calculatePrice(): Int {
        return itemPrice.minus(discountPrice)
    }

    override fun toString(): String {
        return "Order(memberId=$memberId, itemName='$itemName', itemPrice=$itemPrice, discountPrice=$discountPrice)"
    }
}
