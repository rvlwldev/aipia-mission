package com.aipia.application.order

import com.aipia.application.member.Member
import com.aipia.application.product.Product
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @OneToMany(mappedBy = "order", cascade = [ALL], fetch = EAGER, orphanRemoval = true)
    val items: MutableList<OrderItem> = mutableListOf()
) {
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
        val map = this.items.associateBy { it.product.id }
            .toMutableMap()

        additionalOrderItems.forEach { orderItem ->
            val item = map[orderItem.product.id]
                ?.run { increaseCapacity(orderItem.capacity) }

            if (item == null)
                map[orderItem.product.id] = orderItem
        }

        this.items.clear()
        this.items.addAll(map.values)
        this.totalPrice = this.items.sumOf { it.product.price * it.capacity }
    }

    fun markPaid() {
        this.status = OrderStatus.PAID
    }

    fun markFailed(reason: String?) {
        this.status = OrderStatus.FAILED
        this.reason = reason
    }

    companion object {
        fun create(member: Member, products: Map<Product, Int>): Order {
            /**
             * NOTE
             * 현재는 RestAPI 만 사용하며 DTO 레벨에서 입력값을 검증함으로 주석 처리합니다.
             * 추후 주문이 생성되는 진입점이 많아지면 검증 로직의 응집을 고려할 수 있습니다.
             * */
//            check(products.values.any { it > 0 }) {
//                throw IllegalArgumentException("1개 이상 주문가능합니다.")
//            }

            val order = Order(member = member)
            val items = products.map { (product, capacity) ->
                OrderItem(order = order, product = product, capacity = capacity)
            }

            order.addAllItems(items)

            return order
        }
    }
}