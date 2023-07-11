package com.mateuszholik.data.repositories.models

import java.time.LocalDateTime

sealed class PasswordValidity(open val expirationDate: LocalDateTime) {
    data class Valid(override val expirationDate: LocalDateTime) :
        PasswordValidity(expirationDate)

    data class Expiring(override val expirationDate: LocalDateTime) :
        PasswordValidity(expirationDate)

    data class Expired(override val expirationDate: LocalDateTime) :
        PasswordValidity(expirationDate)
}
