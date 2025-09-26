package com.aipia.infra.repository

import com.aipia.domain.Order
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface OrderJpaRepository : JpaRepository<Order, Long> {

    @Query("select o from Order o where o.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = ["items", "items.product"])
    fun findByIdOrNullWithPessimisticLock(id: Long): Order?

}