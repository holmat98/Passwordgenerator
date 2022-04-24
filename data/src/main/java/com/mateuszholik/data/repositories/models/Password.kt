package com.mateuszholik.data.repositories.models

import java.time.LocalDateTime

data class Password(
    val id: Long,
    val platformName: String,
    val password: String,
    val expiringDate: LocalDateTime
)
