package com.aipia.application.port

import com.aipia.domain.Product

interface ProductRepository {
    fun saveAll(products: Collection<Product>): List<Product>
    fun findAllExactly(ids: Collection<Long>): List<Product>
    fun findAllForUpdate(ids: Collection<Long>): List<Product>
}