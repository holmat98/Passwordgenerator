package com.mateuszholik.passwordgenerator.managers

import android.content.ClipData
import android.content.Context
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.providers.MessageProvider

interface ClipboardManager {

    fun copyToClipboard(label: String, text: String)
}

class ClipboardManagerImpl(
    private val context: Context,
    private val messageProvider: MessageProvider
) : ClipboardManager {

    override fun copyToClipboard(label: String, text: String) {
        val clipboard =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager

        val clipData = ClipData.newPlainText(label, text)

        clipboard.setPrimaryClip(clipData)

        messageProvider.show(R.string.message_password_copied_to_clipboard)
    }
}