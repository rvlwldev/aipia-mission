package com.aipia.application.member.port

import com.aipia.application.member.Member

interface TokenManager {
    fun generate(member: Member): String
    fun validate(token: String): Boolean
}