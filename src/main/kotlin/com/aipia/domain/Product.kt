package com.aipia.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
open class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    name: String,
    price: Int,
    capacity: Int
) {
    var name: String = name
        protected set

    var price: Int = price
        protected set

    var capacity: Int = capacity
        protected set

    fun decreaseCapacity(amount: Int) {
        check(this.capacity - amount >= 0) { "재고가 부족합니다." }
        this.capacity -= amount
    }
}
