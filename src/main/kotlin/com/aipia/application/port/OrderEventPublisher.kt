package com.aipia.application.port

import com.aipia.application.event.OrderCreatedEvent

interface OrderEventPublisher {

    fun publishNewOrder(event: OrderCreatedEvent)

}