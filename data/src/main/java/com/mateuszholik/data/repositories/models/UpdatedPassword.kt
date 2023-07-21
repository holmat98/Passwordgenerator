package com.mateuszholik.data.repositories.models

data class UpdatedPassword(
    val id: Long,
    val platformName: String,
    val password: String,
    val website: String?,
    val passwordScore: Int,
    val isExpiring: Boolean,
)
