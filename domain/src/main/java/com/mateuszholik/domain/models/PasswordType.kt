package com.mateuszholik.domain.models

import com.mateuszholik.data.repositories.models.PasswordInfo

sealed class PasswordType(
    open val passwordInfo: PasswordInfo,
    open val score: Int,
) {

    data class ValidPassword(
        override val passwordInfo: PasswordInfo,
        override val score: Int
    ) : PasswordType(passwordInfo, score)

    data class OutdatedPassword(
        override val passwordInfo: PasswordInfo,
        override val score: Int
    ) : PasswordType(passwordInfo, score)

    data class ExpiringPassword(
        override val passwordInfo: PasswordInfo,
        override val score: Int,
    ) : PasswordType(passwordInfo, score)
}
