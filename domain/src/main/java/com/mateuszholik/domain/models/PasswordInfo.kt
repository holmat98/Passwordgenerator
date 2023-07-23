package com.mateuszholik.domain.models

import com.mateuszholik.data.repositories.models.PasswordInfo

internal data class PasswordInfo(
    val passwordInfo: PasswordInfo,
    val passwordScore: Int,
)
