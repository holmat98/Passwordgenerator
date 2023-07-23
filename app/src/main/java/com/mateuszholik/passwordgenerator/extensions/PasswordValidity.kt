package com.mateuszholik.passwordgenerator.extensions

import com.mateuszholik.data.repositories.models.PasswordValidity
import com.mateuszholik.passwordgenerator.R
import java.time.LocalDateTime

internal fun PasswordValidity.getAttrColorResId(): Int =
    when (this) {
        is PasswordValidity.Expiring -> R.attr.colorTertiary
        is PasswordValidity.Expired -> R.attr.colorError
        is PasswordValidity.NeverExpires,
        is PasswordValidity.Valid -> R.attr.colorPrimary
    }

internal val PasswordValidity.expirationDate: LocalDateTime?
    get() = when (this) {
        is PasswordValidity.Expired -> this.expirationDate
        is PasswordValidity.Expiring -> this.expirationDate
        is PasswordValidity.Valid -> this.expirationDate
        PasswordValidity.NeverExpires -> null
    }
