package com.aipia.infra.repository

import com.aipia.domain.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import jakarta.persistence.LockModeType

interface ProductJpaRepository : JpaRepository<Product, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id in :ids")
    fun findAllByIdInWithPessimisticLock(ids: List<Long>): List<Product>
}