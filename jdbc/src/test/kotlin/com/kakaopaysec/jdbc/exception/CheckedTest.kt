package com.kakaopaysec.jdbc.exception

internal  class CheckedTest {

    class MyCheckedException(override val message: String?): Exception(message) {

    }

    class Service {

    }

    class Repository {
        fun call() {
            throw MyCheckedException("ex")
        }
    }
}
