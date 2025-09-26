package com.aipia.application.service

import com.aipia.application.exception.DuplicatedMemberNameException
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
    fun createMember(name: String, password: String, nickname: String): Member {
        require(repo.findByName(name) == null) {
            throw DuplicatedMemberNameException()
        }

        val encodedPassword = passwordManager.encrypt(password)
        val member = Member(name = name, encryptedPassword = encodedPassword, nickname = nickname)

        return repo.save(member)
    }

}