package com.aipia.application.member

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "members")
open class Member(
    @Id val id: String = "",
    encryptedPassword: String,
    nickname: String,
    role: String = "ROLE_USER",
    point: Int = 1_000_000
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
    var point: Int = point
        protected set

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()

    fun decreasePoint(amount: Int) {
        check(this.point - amount >= 0) { "포인트가 부족합니다." }
        this.point -= amount
    }
}