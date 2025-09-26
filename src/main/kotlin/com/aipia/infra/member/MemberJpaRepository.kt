package com.aipia.infra.member

import com.aipia.application.member.Member
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface MemberJpaRepository : JpaRepository<Member, String> {

    @Query("select m from Member m where m.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByIdOrNullWithPessimisticLock(id: String): Member?

}