package com.aipia.api.rest.order.response

import com.aipia.domain.Order

data class OrderResponse(
    val id: Long, val items: List<OrderItemResponse>, val totalPrice: Int, val status: String, val reason: String?
) {
    constructor(order: Order) : this(
        id = order.id,
        items = order.items.map { OrderItemResponse(it) },
        totalPrice = order.totalPrice,
        status = order.status.toString(),
        reason = order.reason
    )
}