package com.aipia.infra

import com.aipia.application.port.OrderRepository
import com.aipia.domain.Order
import com.aipia.infra.repository.OrderJpaRepository
import org.springframework.stereotype.Component

@Component
class OrderRepositoryAdapter(private val jpa: OrderJpaRepository) : OrderRepository {

    override fun save(order: Order) =
        jpa.save(order)

}