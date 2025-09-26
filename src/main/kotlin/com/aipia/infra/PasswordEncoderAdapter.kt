package com.aipia.infra

import com.aipia.application.port.PasswordManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoderAdapter(private val encoder: PasswordEncoder) : PasswordManager {

    override fun encrypt(rawPassword: String): String =
        encoder.encode(rawPassword)

    override fun isValid(rawPassword: String, encrypted: String): Boolean =
        encoder.matches(rawPassword, encrypted)

}