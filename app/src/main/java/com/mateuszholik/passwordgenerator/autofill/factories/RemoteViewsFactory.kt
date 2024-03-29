package com.mateuszholik.passwordgenerator.autofill.factories

import android.widget.RemoteViews
import com.mateuszholik.passwordgenerator.R

interface RemoteViewsFactory {

    fun create(
        packageName: String,
        promptMessage: String,
    ): RemoteViews
}

internal class RemoteViewsFactoryImpl : RemoteViewsFactory {

    override fun create(
        packageName: String,
        promptMessage: String,
    ): RemoteViews =
        RemoteViews(packageName, R.layout.item_autofill_data).apply {
            setTextViewText(R.id.autofillLabel, promptMessage)
        }
}
