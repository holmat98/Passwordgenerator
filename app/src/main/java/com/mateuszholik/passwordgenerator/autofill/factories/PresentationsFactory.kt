package com.mateuszholik.passwordgenerator.autofill.factories

import android.os.Build
import android.service.autofill.Presentations
import androidx.annotation.RequiresApi

interface PresentationsFactory {

    fun create(
        packageName: String,
        promptMessage: String,
    ): Presentations

    fun createWithImage(
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun createWithImage(packageName: String, promptMessage: String): Presentations =
        Presentations.Builder()
            .setDialogPresentation(remoteViewsFactory.createWithImage(packageName, promptMessage))
            .build()
}
