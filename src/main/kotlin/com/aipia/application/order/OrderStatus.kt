package com.aipia.application.order

enum class OrderStatus(val step: Int) {
    CREATED(1),
    PAID(2),
    FAILED(0)
}