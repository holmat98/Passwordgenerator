package com.mateuszholik.passwordgenerator.autofill.factories

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.slice.Slice
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.service.autofill.InlinePresentation
import android.service.autofill.Presentations
import android.util.Size
import android.widget.inline.InlinePresentationSpec
import androidx.annotation.RequiresApi
import androidx.autofill.inline.v1.InlineSuggestionUi
import com.mateuszholik.passwordgenerator.R

interface PresentationsFactory {

    fun create(
        packageName: String,
        promptMessage: String,
        inlinePresentationSpec: InlinePresentationSpec?,
    ): Presentations
}

@SuppressLint("RestrictedApi")
internal class PresentationsFactoryImpl(
    private val remoteViewsFactory: RemoteViewsFactory,
    private val context: Context,
) : PresentationsFactory {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun create(
        packageName: String,
        promptMessage: String,
        inlinePresentationSpec: InlinePresentationSpec?,
    ): Presentations =
        Presentations.Builder()
            .setDialogPresentation(remoteViewsFactory.create(packageName, promptMessage))
            .setInlinePresentation(
                InlinePresentation(
                    createSlice(promptMessage),
                    inlinePresentationSpec ?: InlinePresentationSpec.Builder(
                        Size(100, 30), Size(1000, 30)
                    ).build(),
                    false
                )
            )
            .build()

    private fun createSlice(promptMessage: String): Slice =
        InlineSuggestionUi.newContentBuilder(
            PendingIntent.getService(
                context,
                PENDING_INTENT_REQUEST_CODE,
                Intent(),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
            .setContentDescription(promptMessage)
            .setTitle(promptMessage)
            .setStartIcon(Icon.createWithResource(context, R.drawable.ic_launcher_foreground))
            .build()
            .slice

    private companion object {
        const val PENDING_INTENT_REQUEST_CODE = 10
    }
}
