package com.mateuszholik.data.repositories.models

data class PasswordDetails(
    val id: Long,
    val platformName: String,
    val password: String,
    val website: String,
    val passwordScore: Int,
    val passwordValidity: PasswordValidity,
)
