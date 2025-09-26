package com.aipia.application.member

import com.aipia.application.member.exception.DuplicatedMemberIdException
import com.aipia.application.member.exception.InvalidPasswordException
import com.aipia.application.member.exception.MemberNotFoundException
import com.aipia.application.member.port.MemberRepository
import com.aipia.application.member.port.PasswordManager
import com.aipia.application.member.port.TokenManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val repository: MemberRepository,
    private val passwordManager: PasswordManager,
    private val tokenManager: TokenManager
) {

    @Transactional
    fun createMember(id: String, password: String, nickname: String): Member {
        require(repository.find(id) == null) {
            throw DuplicatedMemberIdException()
        }

        val member = Member(id, passwordManager.encrypt(password), nickname)

        return repository.save(member)
    }

    @Transactional
    fun login(id: String, password: String): String {
        val member = repository.find(id)
            ?: throw MemberNotFoundException()

        if (!passwordManager.isValid(password, member.password)) {
            throw InvalidPasswordException()
        }

        return tokenManager.generate(member)
    }

}