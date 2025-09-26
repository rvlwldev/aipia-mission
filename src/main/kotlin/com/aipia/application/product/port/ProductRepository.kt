package com.aipia.application.product.port

import com.aipia.application.product.Product

interface ProductRepository {
    fun saveAll(products: Collection<Product>): List<Product>
    fun findAllExactly(ids: Collection<Long>): List<Product>
    fun findAllForUpdate(ids: Collection<Long>): List<Product>
}