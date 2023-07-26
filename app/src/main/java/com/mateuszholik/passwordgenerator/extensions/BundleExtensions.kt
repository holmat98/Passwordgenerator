package com.mateuszholik.passwordgenerator.extensions

import android.os.Build
import android.os.Bundle

internal fun <T> Bundle.fromParcelable(key: String, clazz: Class<T>): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, clazz)
    } else {
        this.getParcelable(key)
    }
