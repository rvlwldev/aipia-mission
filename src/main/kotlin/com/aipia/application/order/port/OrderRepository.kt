package com.aipia.application.order.port

import com.aipia.application.order.Order

interface OrderRepository {
    fun save(order: Order): Order
    fun find(id: Long): Order?
    fun findForUpdate(id: Long): Order?
}