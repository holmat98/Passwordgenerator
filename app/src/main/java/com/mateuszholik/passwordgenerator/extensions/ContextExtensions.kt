package com.mateuszholik.passwordgenerator.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes

internal fun Context.getAttrColor(@AttrRes colorResId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(colorResId, typedValue, true)

    return typedValue.data
}
