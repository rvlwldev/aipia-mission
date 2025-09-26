package com.aipia.api.rest.auth.request

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignupRequest(
    @field:Pattern(
        regexp = "^[a-zA-Z0-9]+$",
        message = "아이디는 영어, 숫자만 사용할 수 있습니다."
    )
    @field:Size(min = 4, max = 30, message = "아이디는 최소 4글자 이상, 30글자 이하입니다.")
    val id: String,

    @field:Pattern(
        regexp = "^(?=.{4,50}$)(?=.*[!@#$%^&*(),.?\":{}|<>\\-])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>\\-]+$",
        message = "비밀번호는 최소 4글자 이상, 공백을 제외한 특수문자를 하나 이상 포함해야 합니다."
    )
    @field:Size(max = 50, message = "비말번호는 50자를 넘을 수 없습니다.")
    val password: String,

    @field:Pattern(
        regexp = "^[가-힣a-zA-Z0-9]+$",
        message = "닉네임은 한글, 영어, 숫자만 사용할 수 있습니다."
    )
    @field:Size(min = 2, max = 8, message = "닉네임은 최소 2글자 이상, 8글자 이하입니다.")
    val nickname: String
)

