package com.aipia.application.service

import com.aipia.application.event.OrderCreatedEvent
import com.aipia.application.exception.MemberNotFoundException
import com.aipia.application.port.MemberRepository
import com.aipia.application.port.OrderEventPublisher
import com.aipia.application.port.OrderRepository
import com.aipia.application.port.ProductRepository
import com.aipia.domain.Order
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val memberRepository: MemberRepository,
    private val productRepository: ProductRepository,
    private val eventPublisher: OrderEventPublisher
) {

    @Transactional
    fun createNewOrder(memberId: String, items: Map<Long, Int>): Order {
        val member = memberRepository.find(memberId)
            ?: throw MemberNotFoundException()
        val products = items.map { entry -> entry.key }
            .let { ids -> productRepository.findAllExactly(ids) }
            .associateWith { product -> items.getValue(product.id) }
        val order = orderRepository.save(Order.create(member, products))
            .also { check(it.items.size == items.size) { "존재하지 않는 상품이 포함되어 있습니다." } }

        val event = OrderCreatedEvent(member.id, order.id)
        eventPublisher.publishNewOrder(event)

        return order
    }

}