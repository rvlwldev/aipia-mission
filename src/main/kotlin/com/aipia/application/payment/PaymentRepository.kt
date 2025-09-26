package com.aipia.application.payment

interface PaymentRepository {
    fun createWithJoin(memberId: String, orderId: Long): Payment

    fun save(payment: Payment): Payment

    fun isExist(orderId: Long, status: PaymentStatus): Boolean
}