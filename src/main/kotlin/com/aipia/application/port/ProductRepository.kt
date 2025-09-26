package com.aipia.application.port

import com.aipia.domain.Product

interface ProductRepository {
    fun findAllExactly(ids: Collection<Long>): List<Product>
}