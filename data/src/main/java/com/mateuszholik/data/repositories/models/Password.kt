package com.mateuszholik.data.repositories.models

import java.util.Date

data class Password(
    val id: Long,
    val platformName: String,
    val password: String,
    val expiringDate: Date
)
