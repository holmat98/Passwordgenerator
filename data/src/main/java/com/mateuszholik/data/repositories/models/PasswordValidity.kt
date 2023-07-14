package com.mateuszholik.data.repositories.models

import java.time.LocalDateTime

sealed class PasswordValidity {

    data class Valid(val expirationDate: LocalDateTime) : PasswordValidity()
    data class Expiring(val expirationDate: LocalDateTime) : PasswordValidity()
    data class Expired(val expirationDate: LocalDateTime) : PasswordValidity()
    object NeverExpires : PasswordValidity()
}
