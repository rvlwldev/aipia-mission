package com.aipia.application.port

import com.aipia.domain.Order

interface OrderRepository {
    fun save(order: Order): Order
    fun find(id: Long): Order?
    fun findForUpdate(id: Long): Order?
}