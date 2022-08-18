package com.kakaopaysec.jdbc.member.domain.repository.ex

class MyDbException: RuntimeException {

    constructor(message: String, throwable: Throwable): super(message, throwable) {}
    constructor(message: String): super(message) {}
    constructor(throwable: Throwable): super(throwable) {}

}
