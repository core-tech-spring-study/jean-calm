package com.kakaopaysec.advanced.log.trace

import java.util.UUID

class TraceId {

    val id: String
    var level: Int

    constructor()  {
        this.id = createId()
        this.level = 0
    }

    private constructor(id: String, level: Int) {
        this.id = id
        this.level = level
    }

    private fun createId(): String {
        return UUID.randomUUID().toString().substring(0, 8)
    }

    fun createNextId(): TraceId {
       return TraceId(id = this.id, level = this.level + 1)
    }

    fun createPreviousId(): TraceId {
        return TraceId(id = this.id, level = this.level - 1)
    }

    fun isFirstLevel(): Boolean {
        return level == 0
    }

    override fun toString(): String {
        return "TraceId(id='$id', level=$level)"
    }
}
