package com.mateuszholik.data.repositories.models

data class NewPassword(
    val platformName: String,
    val password: String,
    val website: String?,
    val passwordScore: Int,
    val isExpiring: Boolean,
)
