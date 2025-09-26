package com.aipia.api.rest.order.response

import com.aipia.application.order.OrderItem

data class OrderItemResponse(val name: String, val capacity: Int) {
    constructor(item: OrderItem) : this(name = item.product.name, capacity = item.capacity)
}