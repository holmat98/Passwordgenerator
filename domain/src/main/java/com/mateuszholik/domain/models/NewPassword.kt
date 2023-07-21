package com.mateuszholik.domain.models

data class NewPassword(
    val platformName: String,
    val password: String,
    val website: String?,
    val isExpiring: Boolean,
)
