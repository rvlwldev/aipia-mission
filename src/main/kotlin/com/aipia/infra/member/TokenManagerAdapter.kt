package com.aipia.infra.member

import com.aipia._config.security.jwt.JwtManager
import com.aipia.application.member.Member
import com.aipia.application.member.port.TokenManager
import org.springframework.stereotype.Component

@Component
class TokenManagerAdapter(private val jwtManager: JwtManager) : TokenManager {
    override fun generate(member: Member) =
        jwtManager.generate(member)

    override fun validate(token: String): Boolean {
        jwtManager.validateAndExtractClaims(token)
        return true
    }
}