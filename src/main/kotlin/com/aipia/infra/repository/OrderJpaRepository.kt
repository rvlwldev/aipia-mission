package com.aipia.infra.repository

import com.aipia.domain.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository : JpaRepository<Order, Long>