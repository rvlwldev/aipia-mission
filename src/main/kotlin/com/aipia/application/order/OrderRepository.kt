package com.aipia.application.order

interface OrderRepository {
    fun save(order: Order): Order
    fun find(id: Long): Order?
    fun findForUpdate(id: Long): Order?
}