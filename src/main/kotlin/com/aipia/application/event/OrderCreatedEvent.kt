package com.aipia.application.event

data class OrderCreatedEvent(val memberId: String, val orderId: Long)