package com.mateuszholik.passwordgenerator.extensions

import android.content.Context
import android.util.TypedValue
import android.view.autofill.AutofillManager
import androidx.annotation.AttrRes
import timber.log.Timber

internal fun Context.getAttrColor(@AttrRes colorResId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(colorResId, typedValue, true)

    return typedValue.data
}

internal fun Context.getAutofillManager(): AutofillManager =
    getSystemService(AutofillManager::class.java)

internal fun AutofillManager.shouldAskToGrantPermission(): Boolean =
    try {
        !hasEnabledAutofillServices() && isAutofillSupported
    } catch(exception: Exception) {
        Timber.e(exception, "Error while getting information if autofill is enabled")
        isAutofillSupported
    }
