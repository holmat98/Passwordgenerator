package com.mateuszholik.domain.extensions

import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.models.PasswordType
import java.time.LocalDateTime

internal fun Password.toPasswordType(currentDate: LocalDateTime): PasswordType =
    when {
        currentDate.isBefore(expiringDate.minusDays(7)) -> this.toValidPassword()
        currentDate.isAfter(expiringDate) -> this.toOutdatedPassword()
        else -> this.toExpiringPassword()
    }

private fun Password.toOutdatedPassword(): PasswordType.OutdatedPassword =
    PasswordType.OutdatedPassword(this)

private fun Password.toExpiringPassword(): PasswordType.ExpiringPassword =
    PasswordType.ExpiringPassword(this)

private fun Password.toValidPassword(): PasswordType.ValidPassword =
    PasswordType.ValidPassword(this)