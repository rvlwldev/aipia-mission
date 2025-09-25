package com.aipia.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "members")
open class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "member_name", unique = true, nullable = false)
    val name: String,

    encryptedPassword: String,
    nickname: String,
    role: String = "ROLE_USER",
    point: Long = 1_000_000L,

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    @Column(nullable = false)
    var password: String = encryptedPassword
        protected set

    @Column(unique = true, nullable = false)
    var nickname: String = nickname
        protected set

    @Column(name = "member_role", nullable = false)
    var role: String = role
        protected set

    @Column(nullable = false)
    var point: Long = point
        protected set

}