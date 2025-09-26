package com.aipia.domain

import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity(name = "orders")
open class Order(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @OneToMany(mappedBy = "order", cascade = [ALL], orphanRemoval = true)
    private val _orderItems: MutableList<OrderItem> = mutableListOf()
) {
    val items: List<OrderItem>
        get() = this._orderItems.toList()

    var totalPrice: Int = 0
        protected set

    @Enumerated(STRING)
    var status: OrderStatus = OrderStatus.CREATED
        protected set

    var reason: String? = null
        protected set

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var lastUpdatedAt: LocalDateTime? = null
        protected set

    fun addAllItems(additionalOrderItems: List<OrderItem>) {
        val map = this._orderItems.associateBy { it.product.id }
            .toMutableMap()

        additionalOrderItems.forEach { orderItem ->
            val item = map[orderItem.product.id]
                ?.run { increaseCapacity(orderItem.capacity) }

            if (item == null)
                map[orderItem.product.id] = orderItem
        }

        this._orderItems.clear()
        this._orderItems.addAll(map.values)
        this.totalPrice = this._orderItems.sumOf { it.product.price * it.capacity }
    }

    companion object {
        fun create(member: Member, products: Map<Product, Int>): Order {
            check(products.values.any { it < 1 }) {
                throw IllegalArgumentException("1개 이상 주문가능합니다.")
            }

            val order = Order(member = member)
            val items = products.map { (product, capacity) ->
                OrderItem(order = order, product = product, capacity = capacity)
            }

            order.addAllItems(items)

            return order
        }
    }
}