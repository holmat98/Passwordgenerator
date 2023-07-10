package com.mateuszholik.data.repositories.models

data class PasswordInfo(
    val id: Long,
    val platformName: String,
    val passwordScore: Int,
    val passwordValidity: PasswordValidity,
)
