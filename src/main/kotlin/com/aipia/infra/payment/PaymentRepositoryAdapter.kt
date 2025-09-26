package com.aipia.infra.payment

import com.aipia.application.payment.Payment
import com.aipia.application.payment.port.PaymentRepository
import com.aipia.application.payment.PaymentStatus
import com.aipia.infra.member.MemberJpaRepository
import com.aipia.infra.order.OrderJpaRepository
import org.springframework.stereotype.Component

@Component
class PaymentRepositoryAdapter(
    private val paymentJpaRepository: PaymentJpaRepository,
    private val memberJpaRepository: MemberJpaRepository,
    private val orderJpaRepository: OrderJpaRepository
) : PaymentRepository {

    override fun createWithJoin(memberId: String, orderId: Long): Payment {
        val member = memberJpaRepository.findByIdOrNullWithPessimisticLock(memberId)
        val order = orderJpaRepository.findByIdOrNullWithPessimisticLock(orderId)

        return Payment(
            memberId = memberId, member = member,
            orderId = orderId, order = order,
            amount = order?.totalPrice ?: 0,
        )
    }

    override fun save(payment: Payment) =
        paymentJpaRepository.save(payment)

    override fun isExist(orderId: Long, status: PaymentStatus) =
        paymentJpaRepository.existsByOrderIdAndStatus(orderId, status)
}