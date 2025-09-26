package com.aipia.application.port

import com.aipia.domain.Member

interface MemberRepository {

    fun save(member: Member): Member
    fun find(id: String): Member?

}