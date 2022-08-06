package com.kakaopaysec.core.singleton

class StatefulService {

    fun order(name: String, price: Int): Int {
        println("name = $name, price = $price")
        return price
    }
}
