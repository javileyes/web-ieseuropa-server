package com.ieseuropa.spring.config.exception

class UnauthorizedException: RuntimeException {
    constructor(message: String?): super(message)
    constructor(): super("BadRequest")
}