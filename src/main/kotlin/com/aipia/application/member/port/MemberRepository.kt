package com.aipia.application.member.port

import com.aipia.application.member.Member

interface MemberRepository {

    fun save(member: Member): Member
    fun find(id: String): Member?
    fun findForUpdate(id: String): Member?

}