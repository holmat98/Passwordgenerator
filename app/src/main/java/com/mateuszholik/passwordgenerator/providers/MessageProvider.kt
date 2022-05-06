package com.mateuszholik.passwordgenerator.providers

import androidx.annotation.StringRes

interface MessageProvider {

    fun show(@StringRes message: Int)
}