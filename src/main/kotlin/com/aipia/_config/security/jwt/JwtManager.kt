package com.aipia._config.security.jwt

import com.aipia.application.member.Member
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class JwtManager(private val properties: JwtProperties) {

    private val key = Keys.hmacShaKeyFor(properties.secret.toByteArray(StandardCharsets.UTF_8))

    fun generate(member: Member): String {
        val now = Date()

        return Jwts.builder()
            .subject(member.id) // username
            .claim("role", member.role) // authority
            .issuedAt(now)
            .expiration(Date(now.time + properties.expirationMs))
            .signWith(key)
            .compact()
    }

    fun extractAuthentication(token: String): Authentication {
        val claims = validateAndExtractClaims(token)

        val username = claims.payload.subject
        val authorities = listOf(claims.payload["role"] as String)
            .map { SimpleGrantedAuthority(it) }

        val principal = User(username, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    fun validateAndExtractClaims(token: String): Jws<Claims> {
        try {
            val claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)

            check(claims.payload.containsKey("role")) {
                throw SecurityException()
            }

            return claims
        } catch (e: SecurityException) {
            throw BadCredentialsException("로그인 보안 문제가 감지되었습니다. 다시 로그인해주세요.", e)

        } catch (e: MalformedJwtException) {
            throw BadCredentialsException("유효하지 않은 JWT 토큰입니다.", e)

        } catch (e: ExpiredJwtException) {
            throw BadCredentialsException("만료된 JWT 토큰입니다.", e)

        } catch (e: UnsupportedJwtException) {
            throw BadCredentialsException("지원되지 않는 JWT 토큰입니다.", e)

        } catch (e: IllegalArgumentException) {
            throw BadCredentialsException("JWT 클레임 문자열이 비어있습니다.", e)

        } catch (e: Exception) {
            throw BadCredentialsException("JWT 유효성 검증에 실패했습니다.", e)
        }
    }


}