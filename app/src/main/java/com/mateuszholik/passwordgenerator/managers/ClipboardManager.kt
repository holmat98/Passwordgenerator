package com.mateuszholik.passwordgenerator.managers

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.os.Build
import android.os.PersistableBundle
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.providers.TextProvider

interface ClipboardManager {

    fun copyToClipboard(label: String, text: String)
}

class ClipboardManagerImpl(
    private val context: Context,
    private val messageProvider: MessageProvider,
    private val textProvider: TextProvider
) : ClipboardManager {

    override fun copyToClipboard(label: String, text: String) {
        val clipboard =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager

        val clipData = ClipData.newPlainText(label, text).apply {
            description.extras = PersistableBundle().apply {
                putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
            }
        }

        clipboard.setPrimaryClip(clipData)

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            messageProvider.show(textProvider.provide(MessageType.COPIED_TO_CLIPBOARD))
        }
    }
}
