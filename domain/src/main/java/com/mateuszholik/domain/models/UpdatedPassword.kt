package com.mateuszholik.domain.models

data class UpdatedPassword(
    val id: Long,
    val password: String,
    val platformName: String
)
