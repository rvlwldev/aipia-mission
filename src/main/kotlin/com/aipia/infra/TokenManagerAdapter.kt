package com.aipia.infra

import com.aipia._config.security.jwt.JwtManager
import com.aipia.application.port.TokenManager
import com.aipia.domain.Member
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