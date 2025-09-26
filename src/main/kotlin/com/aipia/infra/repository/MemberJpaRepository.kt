package com.aipia.infra.repository

import com.aipia.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<Member, Long> {

    fun findByName(name: String): Member?

}