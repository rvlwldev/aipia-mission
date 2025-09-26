package com.aipia.application.order.event

data class OrderCreatedEvent(val memberId: String, val orderId: Long)