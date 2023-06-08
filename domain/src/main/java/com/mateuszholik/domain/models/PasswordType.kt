package com.mateuszholik.domain.models

import com.mateuszholik.data.repositories.models.Password

sealed class PasswordType(
    open val password: Password,
    open val score: Int,
) {

    data class ValidPassword(
        override val password: Password,
        override val score: Int
    ) : PasswordType(password, score)

    data class OutdatedPassword(
        override val password: Password,
        override val score: Int
    ) : PasswordType(password, score)

    data class ExpiringPassword(
        override val password: Password,
        override val score: Int,
    ) : PasswordType(password, score)
}
