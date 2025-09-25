package com.aipia._common.exception

import java.time.LocalDateTime

data class BusinessExceptionResponse(
    val status: Int,
    val message: String,
    val path: String? = null,
    val details: List<String>? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
