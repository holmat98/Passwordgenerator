package com.mateuszholik.data.extensions

import com.mateuszholik.data.repositories.models.PasswordValidity
import java.time.LocalDateTime

internal fun LocalDateTime?.toPasswordValidity(): PasswordValidity {
    val now = LocalDateTime.now()

    return when {
        this == null -> PasswordValidity.NeverExpires
        now.isBefore(this.minusDays(7L)) ->
            PasswordValidity.Valid(this)
        now.isAfter(this) ->
            PasswordValidity.Expired(this)
        else -> PasswordValidity.Expiring(this)
    }
}
