package com.mateuszholik.data.repositories.models

data class UpdatedPassword(
    val id: Long,
    val password: String,
    val platformName: String
)
