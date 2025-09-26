package com.aipia.infra

import com.aipia.application.port.ProductRepository
import com.aipia.domain.Product
import com.aipia.infra.repository.ProductJpaRepository
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