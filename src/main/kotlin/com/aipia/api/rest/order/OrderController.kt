package com.aipia.api.rest.order

import com.aipia.api.rest.order.request.OrderProductRequest
import com.aipia.api.rest.order.response.OrderResponse
import com.aipia.application.order.OrderService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/order")
class OrderController(private val service: OrderService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@AuthenticationPrincipal user: User, @RequestBody body: List<OrderProductRequest>): OrderResponse {
        val items = body
            .groupBy { it.productId }
            .mapValues { (_, capacities) ->
                capacities.sumOf { it.capacity }
            }

        val order = service.createNewOrder(user.username, items)

        return OrderResponse(order)
    }

}