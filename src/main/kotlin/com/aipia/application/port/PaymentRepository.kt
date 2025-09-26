package com.aipia.application.port

import com.aipia.domain.Payment
import com.aipia.domain.PaymentStatus

interface PaymentRepository {
    fun createWithJoin(memberId: String, orderId: Long): Payment

    fun save(payment: Payment): Payment

    fun isExist(orderId: Long, status: PaymentStatus): Boolean
}
