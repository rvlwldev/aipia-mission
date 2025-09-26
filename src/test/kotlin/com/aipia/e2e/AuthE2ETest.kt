package com.aipia.e2e

import com.aipia.api.rest.auth.request.LoginRequest
import com.aipia.api.rest.auth.request.SignupRequest
import com.aipia.domain.Member
import com.aipia.infra.repository.MemberJpaRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import kotlin.test.assertNotEquals

@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthE2ETest(
    private val mvc: MockMvc,
    private val repository: MemberJpaRepository,
    private val passwordEncoder: PasswordEncoder,
    private val objectMapper: ObjectMapper,
) {

    val testName = "test"
    val testPassword = "test-password1!@"
    val testNickname = "test"

    val existMemberName = "exist"
    val existMemberPassword = "exist-password!@"
    val existMemberNickname = "exist"

    @BeforeAll
    fun setup() {
        val exist = Member(
            name = existMemberName,
            encryptedPassword = passwordEncoder.encode(existMemberPassword),
            nickname = existMemberNickname
        )

        repository.save(exist)
    }

    @Test
    fun `성공 - 회원가입`() {
        val request = SignupRequest(testName, testPassword, testNickname)

        mvc.post("/api/auth/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isCreated() }
        }

        val member = repository.findByName(request.name)
        assertNotNull(member!!)
        assertEquals(request.name, member.name)
        assertEquals(request.nickname, member.nickname)
        assertNotEquals(request.password, member.password)
        assertTrue(passwordEncoder.matches(request.password, member.password))
    }

    @Test
    fun `성공 - 로그인`() {
        val request = LoginRequest(existMemberName, existMemberPassword)

        mvc.post("/api/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            jsonPath("$.token") { isNotEmpty() }
        }
    }

    @Test
    fun `실패 - 회원가입시 아이디 중복`() {
        val request = SignupRequest(existMemberName, testPassword, testNickname)

        mvc.post("/api/auth/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isConflict() }
        }
    }

    @Test
    fun `실패 - 로그인 없이 URL 접근`() {
        mvc.get("/api/any-protected-url")
            .andExpect {
                status { isUnauthorized() }
            }
    }
}