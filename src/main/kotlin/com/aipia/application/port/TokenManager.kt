package com.aipia.application.port

import com.aipia.domain.Member

interface TokenManager {
    fun generate(member: Member): String
    fun validate(token: String): Boolean
}