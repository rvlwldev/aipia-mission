package com.aipia.infra

import com.aipia.application.port.MemberRepository
import com.aipia.domain.Member
import com.aipia.infra.repository.MemberJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class MemberRepositoryAdapter(private val repo: MemberJpaRepository) : MemberRepository {

    override fun save(member: Member) =
        repo.save(member)

    override fun find(id: String) =
        repo.findByIdOrNull(id)

}