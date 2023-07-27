package com.mateuszholik.passwordgenerator.autofill.builders

import android.app.PendingIntent
import android.app.assist.AssistStructure
import android.content.Context
import android.os.Build
import android.service.autofill.FillResponse
import android.view.autofill.AutofillId
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.autofill.factories.DatasetFactory
import com.mateuszholik.passwordgenerator.autofill.factories.PresentationsFactory
import com.mateuszholik.passwordgenerator.autofill.factories.RemoteViewsFactory
import com.mateuszholik.passwordgenerator.autofill.models.ParsedStructure
import com.mateuszholik.passwordgenerator.ui.autofill.PasswordAutofillActivity

class FillResponseBuilder(
    private val datasetFactory: DatasetFactory,
    private val remoteViewsFactory: RemoteViewsFactory,
    private val presentationsFactory: PresentationsFactory,
) {

    private var fillResponseBuilder = FillResponse.Builder()

    fun addDataset(
        autofillId: AutofillId,
        promptMessage: String,
        autofillValue: String,
        packageName: String,
    ): FillResponseBuilder {
        fillResponseBuilder.addDataset(
            datasetFactory.create(
                autofillId,
                promptMessage,
                autofillValue,
                packageName
            )
        )

        return this
    }

    fun addSelectPasswordDialog(
        context: Context,
        parsedStructure: ParsedStructure,
        assistStructure: AssistStructure,
    ): FillResponseBuilder {
        val intentSender = PendingIntent.getActivity(
            context,
            PENDING_INTENT_REQUEST_ID,
            PasswordAutofillActivity.newIntent(
                context = context,
                assistStructure = assistStructure,
                packageName = parsedStructure.packageName
            ),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        ).intentSender

        val packageName = context.packageName
        val promptMessage =
            context.getString(R.string.autofill_password_authentication_prompt_message)

        val autofillIds = arrayOf(parsedStructure.autofillId)

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            fillResponseBuilder.setAuthentication(
                autofillIds,
                intentSender,
                remoteViewsFactory.create(packageName, promptMessage)
            )
        } else {
            fillResponseBuilder.setAuthentication(
                autofillIds,
                intentSender,
                presentationsFactory.create(packageName, promptMessage)
            )
        }

        return this
    }

    fun build(): FillResponse {
        val fillResponse = fillResponseBuilder.build()

        fillResponseBuilder = FillResponse.Builder()

        return fillResponse
    }

    private companion object {
        const val PENDING_INTENT_REQUEST_ID = 8675
    }
}
