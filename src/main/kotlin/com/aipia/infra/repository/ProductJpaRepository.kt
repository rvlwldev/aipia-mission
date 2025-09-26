package com.aipia.infra.repository

import com.aipia.domain.Order
import com.aipia.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<Product, Long>