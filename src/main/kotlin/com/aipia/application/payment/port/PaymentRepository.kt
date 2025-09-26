package com.aipia.application.payment.port

import com.aipia.application.payment.Payment
import com.aipia.application.payment.PaymentStatus

interface PaymentRepository {
    fun createWithJoin(memberId: String, orderId: Long): Payment

    fun save(payment: Payment): Payment

    fun isExist(orderId: Long, status: PaymentStatus): Boolean
}