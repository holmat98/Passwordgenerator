package com.mateuszholik.passwordgenerator.providers

import android.content.Context
import android.widget.Toast

class ToastProviderImpl(private val context: Context) : MessageProvider {

    override fun show(message: Int) {
        Toast.makeText(context, context.getString(message), Toast.LENGTH_SHORT).show()
    }
}