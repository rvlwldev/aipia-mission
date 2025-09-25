package com.aipia._config.security

import com.aipia.application.port.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(val repo: MemberRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        val member = repo.findByName(username ?: "")
            ?: throw UsernameNotFoundException("해당 회원정보를 찾을 수 없습니다.")
        val authorities = listOf(member.role).map { SimpleGrantedAuthority(it) }

        return User(member.name, null, authorities)
    }
}