package com.aipia.infra.member

import com.aipia.application.member.Member
import com.aipia.application.member.port.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class MemberRepositoryAdapter(private val repo: MemberJpaRepository) : MemberRepository {

    override fun save(member: Member) =
        repo.save(member)

    override fun find(id: String) =
        repo.findByIdOrNull(id)

    override fun findForUpdate(id: String): Member? =
        repo.findByIdOrNullWithPessimisticLock(id)

}