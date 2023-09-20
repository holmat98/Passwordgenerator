package com.mateuszholik.passwordgenerator.autofill.factories

import android.app.PendingIntent
import android.os.Build
import android.service.autofill.Dataset
import android.service.autofill.Field
import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import android.widget.inline.InlinePresentationSpec
import androidx.annotation.RequiresApi

interface DatasetFactory {

    fun create(
        autofillId: AutofillId,
        promptMessage: String,
        autofillValue: String,
        packageName: String,
        inlinePresentationSpec: InlinePresentationSpec? = null
    ): Dataset

    fun createAuthenticationDataset(
        autofillId: AutofillId,
        promptMessage: String,
        packageName: String,
        pendingIntent: PendingIntent,
        inlinePresentationSpec: InlinePresentationSpec? = null,
    ): Dataset
}

internal class DatasetFactoryImpl(
    private val remoteViewsFactory: RemoteViewsFactory,
) : DatasetFactory {

    override fun create(
        autofillId: AutofillId,
        promptMessage: String,
        autofillValue: String,
        packageName: String,
        inlinePresentationSpec: InlinePresentationSpec?,
    ): Dataset =
        Dataset.Builder(remoteViewsFactory.create(packageName, promptMessage))
            .setValue(autofillId, AutofillValue.forText(autofillValue))
            .build()

    override fun createAuthenticationDataset(
        autofillId: AutofillId,
        promptMessage: String,
        packageName: String,
        pendingIntent: PendingIntent,
        inlinePresentationSpec: InlinePresentationSpec?,
    ): Dataset =
        Dataset.Builder(remoteViewsFactory.create(packageName, promptMessage))
            .setValue(autofillId, AutofillValue.forText(promptMessage))
            .setAuthentication(pendingIntent.intentSender)
            .build()
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
internal class Sdk33DatasetFactoryImpl(
    private val presentationsFactory: PresentationsFactory,
) : DatasetFactory {

    override fun create(
        autofillId: AutofillId,
        promptMessage: String,
        autofillValue: String,
        packageName: String,
        inlinePresentationSpec: InlinePresentationSpec?
    ): Dataset =
        Dataset.Builder(
            presentationsFactory.create(
                packageName,
                promptMessage,
                inlinePresentationSpec
            )
        )
            .setField(
                autofillId,
                Field.Builder()
                    .setValue(
                        AutofillValue.forText(autofillValue)
                    )
                    .build()
            )
            .build()

    override fun createAuthenticationDataset(
        autofillId: AutofillId,
        promptMessage: String,
        packageName: String,
        pendingIntent: PendingIntent,
        inlinePresentationSpec: InlinePresentationSpec?,
    ): Dataset =
        Dataset.Builder(
            presentationsFactory.create(
                packageName,
                promptMessage,
                inlinePresentationSpec
            )
        )
            .setField(
                autofillId,
                Field.Builder()
                    .setValue(
                        AutofillValue.forText(promptMessage)
                    )
                    .build()
            )
            .setAuthentication(pendingIntent.intentSender)
            .build()

}
