package com.mateuszholik.passwordgenerator.autofill.factories

import android.os.Build
import android.service.autofill.Dataset
import android.service.autofill.Field
import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import androidx.annotation.RequiresApi

interface DatasetFactory {

    fun create(
        autofillId: AutofillId,
        promptMessage: String,
        autofillValue: String,
        packageName: String,
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
    ): Dataset =
        Dataset.Builder(remoteViewsFactory.create(packageName, promptMessage))
            .setValue(autofillId, AutofillValue.forText(autofillValue))
            .build()

}

internal class Sdk33DatasetFactoryImpl(
    private val presentationsFactory: PresentationsFactory,
) : DatasetFactory {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun create(
        autofillId: AutofillId,
        promptMessage: String,
        autofillValue: String,
        packageName: String,
    ): Dataset =
        Dataset.Builder(presentationsFactory.create(packageName, promptMessage))
            .setField(
                autofillId,
                Field.Builder().setValue(
                    AutofillValue.forText(
                        autofillValue
                    )
                ).build()
            )
            .build()

}
