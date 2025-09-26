package com.aipia.application.service

import com.aipia.application.exception.InvalidPasswordException
import com.aipia.application.exception.MemberNotFoundException
import com.aipia.application.port.MemberRepository
import com.aipia.application.port.PasswordManager
import com.aipia.application.port.TokenManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthenticationService(
    private val repository: MemberRepository,
    private val passwordManager: PasswordManager,
    private val tokenManager: TokenManager,
) {

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