package com.aipia.application.product

interface ProductRepository {
    fun saveAll(products: Collection<Product>): List<Product>
    fun findAllExactly(ids: Collection<Long>): List<Product>
    fun findAllForUpdate(ids: Collection<Long>): List<Product>
}