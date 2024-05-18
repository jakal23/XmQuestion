package com.xm.question.domain

class ContractViolationException(
    message: String,
    exception: Throwable
) : RuntimeException(message, exception)