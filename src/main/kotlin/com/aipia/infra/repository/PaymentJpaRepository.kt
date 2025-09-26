package com.aipia.infra.repository

import com.aipia.domain.Payment
import com.aipia.domain.PaymentStatus
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<Payment, Long> {
    fun existsByOrderIdAndStatus(orderId: Long, status: PaymentStatus): Boolean
}
