package com.aipia.api.rest.order.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class OrderProductRequest(
    @field:NotBlank(message = "주문 상품 정보가 누락되었습니다.")
    val productId: Long,

    @field:Min(value = 1, message = "상품은 1개 이상 주문 가능합니다.")
    val capacity: Int
)