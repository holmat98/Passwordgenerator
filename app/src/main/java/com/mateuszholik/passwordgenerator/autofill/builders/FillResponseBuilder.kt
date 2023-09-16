package com.mateuszholik.passwordgenerator.autofill.builders

import android.app.PendingIntent
import android.app.assist.AssistStructure
import android.content.Context
import android.service.autofill.FillResponse
import android.widget.inline.InlinePresentationSpec
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.autofill.factories.DatasetFactory
import com.mateuszholik.passwordgenerator.autofill.models.ParsedStructure
import com.mateuszholik.passwordgenerator.ui.autofill.PasswordAutofillActivity

class FillResponseBuilder(private val datasetFactory: DatasetFactory) {

    private var fillResponseBuilder = FillResponse.Builder()

    fun addDatasetWithItemsAndInAppSelection(
        packageName: String,
        items: List<AutofillPasswordDetails>,
        context: Context,
        assistStructure: AssistStructure,
        parsedStructure: ParsedStructure,
        inlinePresentationSpec: InlinePresentationSpec? = null,
    ): FillResponseBuilder {
        val pendingIntent = createAutofillActivityPendingIntent(
            context = context,
            assistStructure = assistStructure,
            packageName = parsedStructure.packageName
        )

        items.forEach {
            fillResponseBuilder.addDataset(
                datasetFactory.create(
                    autofillId = parsedStructure.autofillId,
                    packageName = packageName,
                    promptMessage = it.platformName,
                    autofillValue = it.password,
                    inlinePresentationSpec = inlinePresentationSpec,
                )
            )
        }

        val promptMessage =
            context.getString(R.string.autofill_password_authentication_prompt_message)

        fillResponseBuilder.addDataset(
            datasetFactory.createAuthenticationDataset(
                autofillId = parsedStructure.autofillId,
                packageName = packageName,
                promptMessage = promptMessage,
                pendingIntent = pendingIntent,
                inlinePresentationSpec = inlinePresentationSpec
            )
        )

        return this
    }

    fun addSelectPasswordDialog(
        context: Context,
        parsedStructure: ParsedStructure,
        assistStructure: AssistStructure,
        inlinePresentationSpec: InlinePresentationSpec? = null,
    ): FillResponseBuilder {
        val pendingIntent = createAutofillActivityPendingIntent(
            context = context,
            assistStructure = assistStructure,
            packageName = parsedStructure.packageName
        )

        val packageName = context.packageName
        val promptMessage =
            context.getString(R.string.autofill_password_authentication_prompt_message)

        fillResponseBuilder.addDataset(
            datasetFactory.createAuthenticationDataset(
                autofillId = parsedStructure.autofillId,
                packageName = packageName,
                promptMessage = promptMessage,
                pendingIntent = pendingIntent,
                inlinePresentationSpec = inlinePresentationSpec,
            )
        )

        return this
    }

    fun build(): FillResponse {
        val fillResponse = fillResponseBuilder.build()

        fillResponseBuilder = FillResponse.Builder()

        return fillResponse
    }

    private fun createAutofillActivityPendingIntent(
        context: Context,
        assistStructure: AssistStructure,
        packageName: String?,
    ): PendingIntent =
        PendingIntent.getActivity(
            context,
            PENDING_INTENT_REQUEST_ID,
            PasswordAutofillActivity.newIntent(
                context = context,
                assistStructure = assistStructure,
                packageName = packageName
            ),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )

    private companion object {
        const val PENDING_INTENT_REQUEST_ID = 8675
    }
}
