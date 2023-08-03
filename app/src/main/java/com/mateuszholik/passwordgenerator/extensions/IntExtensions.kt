package com.mateuszholik.passwordgenerator.extensions

import android.content.Context

fun Int.toDp(context: Context): Int {
    val scale = context.resources.displayMetrics.density

    return (this * scale + 0.5f).toInt()
}
