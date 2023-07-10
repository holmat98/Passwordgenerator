package com.mateuszholik.passwordgenerator.extensions

import com.mateuszholik.data.repositories.models.PasswordValidity
import com.mateuszholik.passwordgenerator.R

internal fun PasswordValidity.getAttrColorResId(): Int =
    when (this) {
        PasswordValidity.EXPIRING -> R.attr.colorTertiary
        PasswordValidity.EXPIRED -> R.attr.colorError
        PasswordValidity.VALID -> R.attr.colorPrimary
    }
