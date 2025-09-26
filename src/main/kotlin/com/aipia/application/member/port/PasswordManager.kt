package com.aipia.application.member.port

interface PasswordManager {
    fun encrypt(rawPassword: String): String
    fun isValid(rawPassword: String, encrypted: String): Boolean
}