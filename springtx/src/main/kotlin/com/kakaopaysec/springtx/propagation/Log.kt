package com.kakaopaysec.springtx.propagation

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Log(
    val message: String
) {

    @Id
    @GeneratedValue
    var id: Long? = null
}
