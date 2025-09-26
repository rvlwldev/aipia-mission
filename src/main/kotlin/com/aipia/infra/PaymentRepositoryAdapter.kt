package com.aipia.infra

import com.aipia.application.port.PaymentRepository
import com.aipia.domain.Payment
import com.aipia.domain.PaymentStatus
import com.aipia.infra.repository.MemberJpaRepository
import com.aipia.infra.repository.OrderJpaRepository
import com.aipia.infra.repository.PaymentJpaRepository
import org.springframework.stereotype.Component

@Component
class PaymentRepositoryAdapter(
    private val paymentJpa: PaymentJpaRepository,
    private val memberJpa: MemberJpaRepository,
    private val orderJpa: OrderJpaRepository,
) : PaymentRepository {

    override fun createWithJoin(memberId: String, orderId: Long): Payment {
        val member = memberJpa.findByIdOrNullWithPessimisticLock(memberId)
        val order = orderJpa.findByIdOrNullWithPessimisticLock(orderId)

        return Payment(
            memberId = memberId, member = member,
            orderId = orderId, order = order,
            amount = order?.totalPrice ?: 0,
        )
    }

    override fun save(payment: Payment): Payment {
        return paymentJpa.save(payment)
    }

    override fun isExist(orderId: Long, status: PaymentStatus) =
        paymentJpa.existsByOrderIdAndStatus(orderId, status)

}
