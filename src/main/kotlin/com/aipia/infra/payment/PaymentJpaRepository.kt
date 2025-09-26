package com.aipia.infra.payment

import com.aipia.application.payment.Payment
import com.aipia.application.payment.PaymentStatus
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<Payment, Long> {
    fun findByOrderId(orderId: Long): Payment?
    fun existsByOrderIdAndStatus(orderId: Long, status: PaymentStatus): Boolean
}
