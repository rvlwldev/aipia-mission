package com.aipia.integration

import com.aipia.application.member.Member
import com.aipia.application.member.exception.MemberNotFoundException
import com.aipia.application.member.port.MemberRepository
import com.aipia.application.order.Order
import com.aipia.application.order.OrderService
import com.aipia.application.order.event.OrderCreatedEvent
import com.aipia.application.order.port.OrderEventPublisher
import com.aipia.application.order.port.OrderRepository
import com.aipia.application.product.Product
import com.aipia.application.product.port.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

class OrderIntegrationTest {

    private lateinit var orderService: OrderService
    private val orderRepository: OrderRepository = mock()
    private val memberRepository: MemberRepository = mock()
    private val productRepository: ProductRepository = mock()
    private val eventPublisher: OrderEventPublisher = mock()

    @BeforeEach
    fun setUp() {
        orderService = OrderService(orderRepository, memberRepository, productRepository, eventPublisher)
    }

    @Test
    fun `성공 - 주문생성`() {
        // Given
        val memberId = "testMember"
        val productId1 = 1L
        val productId2 = 2L
        val items = mapOf(productId1 to 2, productId2 to 1)

        val member = Member(id = memberId, encryptedPassword = "password", nickname = "test")
        val product1 = Product(id = productId1, name = "Product1", price = 1000, capacity = 10)
        val product2 = Product(id = productId2, name = "Product2", price = 2000, capacity = 10)

        whenever(memberRepository.find(memberId)).thenReturn(member)
        whenever(productRepository.findAllExactly(listOf(productId1, productId2))).thenReturn(
            listOf(
                product1, product2
            )
        )
        whenever(orderRepository.save(any<Order>())).thenAnswer { it.arguments[0] as Order }

        // When
        val createdOrder = orderService.createNewOrder(memberId, items)

        // Then
        Assertions.assertNotNull(createdOrder)
        Assertions.assertEquals(memberId, createdOrder.member.id)
        Assertions.assertEquals(2, createdOrder.items.size)
        Assertions.assertEquals(4000, createdOrder.totalPrice) // (1000 * 2) + (2000 * 1)

        verify(orderRepository).save(any<Order>())
        verify(eventPublisher).publishNewOrder(any<OrderCreatedEvent>())
    }

    @Test
    fun `실패 - 비회원 주문 생성`() {
        // Given
        val memberId = "nonExistentMember"
        val items = mapOf(1L to 1)

        whenever(memberRepository.find(memberId)).thenReturn(null)

        // When & Then
        assertThrows<MemberNotFoundException> {
            orderService.createNewOrder(memberId, items)
        }

        verify(memberRepository).find(memberId)
        verifyNoInteractions(productRepository, orderRepository, eventPublisher)
    }

    @Test
    fun `실패 - 존재하지 않는 상품 주문`() {
        val memberId = "testMember"
        val productId1 = 1L
        val productId2 = 2L
        val items = mapOf(productId1 to 2, productId2 to 1)

        val member = Member(id = memberId, encryptedPassword = "password", nickname = "test")
        val product1 = Product(id = productId1, name = "Product1", price = 1000, capacity = 10)

        whenever(memberRepository.find(memberId)).thenReturn(member)
        whenever(productRepository.findAllExactly(listOf(productId1, productId2))).thenReturn(listOf(product1))
        whenever(orderRepository.save(any<Order>())).thenAnswer { it.arguments[0] as Order }

        val exception = assertThrows<IllegalStateException> {
            orderService.createNewOrder(memberId, items)
        }
        Assertions.assertEquals("존재하지 않는 상품이 포함되어 있습니다.", exception.message)

        verify(memberRepository).find(memberId)
        verify(productRepository).findAllExactly(listOf(productId1, productId2))
        verify(eventPublisher, never()).publishNewOrder(any())
    }
}