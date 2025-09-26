package com.aipia.api.rest.auth

import com.aipia.api.rest.auth.request.LoginRequest
import com.aipia.api.rest.auth.request.SignupRequest
import com.aipia.api.rest.auth.response.LoginResponse
import com.aipia.application.service.AuthenticationService
import com.aipia.application.service.MemberService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val authService: AuthenticationService,
    private val memberService: MemberService
) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun postSignup(@RequestBody @Valid body: SignupRequest) {
        with(body) { memberService.createMember(name, password, nickname) }
    }

    @PostMapping("/login")
    fun postLogin(@RequestBody @Valid body: LoginRequest): LoginResponse {
        val token = with(body) { authService.login(body.name, body.password) }
        return LoginResponse(token)
    }

}

