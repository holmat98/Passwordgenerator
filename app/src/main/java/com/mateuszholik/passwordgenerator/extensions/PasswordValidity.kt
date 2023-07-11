package com.mateuszholik.passwordgenerator.extensions

import com.mateuszholik.data.repositories.models.PasswordValidity
import com.mateuszholik.passwordgenerator.R

internal fun PasswordValidity.getAttrColorResId(): Int =
    when (this) {
        is PasswordValidity.Expiring -> R.attr.colorTertiary
        is PasswordValidity.Expired -> R.attr.colorError
        is PasswordValidity.Valid -> R.attr.colorPrimary
    }
