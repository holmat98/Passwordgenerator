package com.mateuszholik.domain.models

import com.mateuszholik.data.repositories.models.Password

sealed class PasswordType(open val password: Password) {

    data class ValidPassword(override val password: Password): PasswordType(password)

    data class OutdatedPassword(override val password: Password): PasswordType(password)

    data class ExpiringPassword(override val password: Password): PasswordType(password)
}
