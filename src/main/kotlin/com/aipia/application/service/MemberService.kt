package com.aipia.application.service

import com.aipia.application.exception.DuplicatedMemberIdException
import com.aipia.application.port.MemberRepository
import com.aipia.application.port.PasswordManager
import com.aipia.domain.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val repo: MemberRepository,
    private val passwordManager: PasswordManager
) {

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun createMember(id: String, password: String, nickname: String): Member {
        require(repo.find(id) == null) {
            throw DuplicatedMemberIdException()
        }

        val member = Member(id, passwordManager.encrypt(password), nickname)

        return repo.save(member)
    }

}