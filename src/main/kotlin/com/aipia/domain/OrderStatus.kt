package com.aipia.domain

enum class OrderStatus(val step: Int) {
    CREATED(1),
    READY(2),
    PAID(3),
    FAIL(0)
}