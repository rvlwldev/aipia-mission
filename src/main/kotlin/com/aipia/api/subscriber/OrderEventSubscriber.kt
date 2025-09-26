package com.aipia.api.subscriber

import com.aipia.application.order.event.OrderCreatedEvent
import com.aipia.application.payment.PaymentService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class OrderEventSubscriber(private val service: PaymentService) {

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun afterCreateNewOrder(event: OrderCreatedEvent) {
        with(event) { service.createPayment(memberId, orderId) }
    }
}