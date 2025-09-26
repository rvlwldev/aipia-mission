package com.aipia.infra.order

import com.aipia.application.order.Order
import com.aipia.application.order.OrderRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class OrderRepositoryAdapter(private val orderJpaRepository: OrderJpaRepository) : OrderRepository {

    override fun save(order: Order) =
        orderJpaRepository.save(order)

    override fun find(id: Long) =
        orderJpaRepository.findByIdOrNull(id)

    override fun findForUpdate(id: Long) =
        orderJpaRepository.findByIdOrNullWithPessimisticLock(id)

}