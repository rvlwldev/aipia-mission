package com.aipia.infra.product

import com.aipia.application.product.Product
import com.aipia.application.product.ProductRepository
import org.springframework.stereotype.Component

@Component
class ProductRepositoryAdapter(private val jpa: ProductJpaRepository) : ProductRepository {

    override fun saveAll(products: Collection<Product>) =
        jpa.saveAll(products)

    override fun findAllExactly(ids: Collection<Long>) =
        jpa.findAllById(ids)

    override fun findAllForUpdate(ids: Collection<Long>) =
        jpa.findAllByIdInWithPessimisticLock(ids.toList())

}