package com.mateuszholik.passwordgenerator.autofill.factories

import android.app.slice.Slice
import android.os.Build
import android.service.autofill.InlinePresentation
import android.service.autofill.Presentations
import androidx.annotation.RequiresApi

interface PresentationsFactory {

    fun create(
        packageName: String,
        promptMessage: String,
    ): Presentations
}

internal class PresentationsFactoryImpl(
    private val remoteViewsFactory: RemoteViewsFactory,
) : PresentationsFactory {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun create(packageName: String, promptMessage: String): Presentations =
        Presentations.Builder()
            .setDialogPresentation(remoteViewsFactory.create(packageName, promptMessage))
            .build()
}
