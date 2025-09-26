package com.aipia.infra

import com.aipia.application.event.OrderCreatedEvent
import com.aipia.application.port.OrderEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class OrderEventPublisherAdapter(private val delegate: ApplicationEventPublisher) : OrderEventPublisher {
    override fun publishNewOrder(event: OrderCreatedEvent) {
        delegate.publishEvent(event)
    }
}
