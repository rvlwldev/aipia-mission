package com.aipia.application.port

import com.aipia.domain.Member

interface MemberRepository {

    fun save(member: Member): Member
    fun findByName(name: String): Member?

}