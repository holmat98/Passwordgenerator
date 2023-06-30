package com.mateuszholik.passwordgenerator.extensions

import com.mateuszholik.domain.models.PasswordType
import com.mateuszholik.passwordgenerator.R

internal fun PasswordType.getAttrColorResId(): Int =
    when (this) {
        is PasswordType.ExpiringPassword -> R.attr.colorTertiary
        is PasswordType.OutdatedPassword -> R.attr.colorError
        is PasswordType.ValidPassword -> R.attr.colorPrimary
    }
