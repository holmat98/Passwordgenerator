package com.mateuszholik.domain.models

import com.mateuszholik.data.repositories.models.Password

internal data class PasswordInfo(
    val password: Password,
    val passwordScore: Int,
)
