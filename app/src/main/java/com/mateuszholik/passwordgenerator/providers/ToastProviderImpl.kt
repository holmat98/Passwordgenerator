package com.mateuszholik.passwordgenerator.providers

import android.content.Context
import android.widget.Toast

class ToastProviderImpl(private val context: Context) : MessageProvider {

    override fun show(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
