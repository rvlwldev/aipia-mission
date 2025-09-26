package com.aipia.application.order.port

import com.aipia.application.order.event.OrderCreatedEvent

interface OrderEventPublisher {

    fun publishNewOrder(event: OrderCreatedEvent)

}