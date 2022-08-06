package com.kakaopaysec.core.singleton

internal class SingletonService private constructor() {

    companion object {
        private val instance = SingletonService()

        fun getInstance(): SingletonService {
            return instance
        }
    }

    fun logic() {
        println("싱글톤 객체 로직 호출")
    }
}
