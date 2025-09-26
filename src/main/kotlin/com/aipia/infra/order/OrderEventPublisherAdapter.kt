package com.aipia.infra.order

import com.aipia.application.order.port.OrderEventPublisher
import com.aipia.application.order.event.OrderCreatedEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class OrderEventPublisherAdapter(private val delegate: ApplicationEventPublisher) : OrderEventPublisher {
    override fun publishNewOrder(event: OrderCreatedEvent) {
        delegate.publishEvent(event)
    }
}
