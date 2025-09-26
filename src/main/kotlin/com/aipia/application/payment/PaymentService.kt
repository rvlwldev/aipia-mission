package com.aipia.application.payment

import com.aipia._common.exception.BusinessException
import com.aipia.application.member.exception.MemberNotFoundException
import com.aipia.application.member.port.MemberRepository
import com.aipia.application.order.OrderRepository
import com.aipia.application.order.exception.OrderNotFoundException
import com.aipia.application.product.ProductNotFoundException
import com.aipia.application.product.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val memberRepository: MemberRepository,
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository
) {

    @Transactional
    fun createPayment(memberId: String, orderId: Long): Payment? {
        if (paymentRepository.isExist(orderId, PaymentStatus.SUCCESS))
            return null

        var payment = Payment(memberId = memberId, orderId = orderId, amount = 0)

        return try {
            payment = paymentRepository.createWithJoin(memberId, orderId)
            val member = payment.member ?: throw MemberNotFoundException()
            val order = payment.order ?: throw OrderNotFoundException()
            val productMap = productRepository
                .findAllForUpdate(order.items.map { it.product.id })
                .associateBy { it.id }

            member.decreasePoint(order.totalPrice)
            order.markPaid()
            order.items.forEach { item ->
                productMap[item.product.id]?.decreaseCapacity(item.capacity) ?: throw ProductNotFoundException()
            }

            memberRepository.save(member)
            orderRepository.save(order)
            productRepository.saveAll(productMap.values)
            paymentRepository.save(payment).also { /**  결제 성공 알림 등 ... */ }
        } catch (e: Exception) {
            when (e) {
                is NullPointerException,
                is BusinessException,
                is IllegalStateException,
                is IllegalArgumentException -> payment.fail(e.message)

                else -> {
                    payment.fail(e.message)
                    // 예외 로그 남기기 생략
                }
            }
            paymentRepository.save(payment).also { /**  결제 실패 알림 등 ... */ }
        }
    }

}