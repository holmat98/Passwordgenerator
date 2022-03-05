package com.mateuszholik.passwordgenerator.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

infix fun View.setIsVisibility(isVisible: Boolean) {
    visibility = if (isVisible) VISIBLE else GONE
}