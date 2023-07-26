package com.mateuszholik.passwordgenerator.extensions

import android.content.Intent
import android.os.Build

inline fun <reified T> Intent.fromParcelable(key: String): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, T::class.java)
    } else {
        this.getParcelableExtra(key) as? T
    }
