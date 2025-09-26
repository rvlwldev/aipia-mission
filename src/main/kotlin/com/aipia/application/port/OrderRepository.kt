package com.aipia.application.port

import com.aipia.domain.Order

interface OrderRepository {
    fun save(order: Order): Order
}