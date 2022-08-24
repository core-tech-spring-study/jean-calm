package com.kakaopaysec.springtx.order

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "orders")
@Entity
class Order {

    @Id
    @GeneratedValue
    var id: Long? = null

    var username: String? = null
    var payStatus: String? = null
}
