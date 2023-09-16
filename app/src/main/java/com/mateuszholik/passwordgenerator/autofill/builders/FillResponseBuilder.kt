package com.mateuszholik.passwordgenerator.autofill.builders

import android.app.PendingIntent
import android.app.assist.AssistStructure
import android.content.Context
import android.service.autofill.FillRequest
import android.service.autofill.FillResponse
import android.util.Size
import android.view.autofill.AutofillId
import android.widget.inline.InlinePresentationSpec
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.autofill.factories.DatasetFactory
import com.mateuszholik.passwordgenerator.autofill.models.ParsedStructure
import com.mateuszholik.passwordgenerator.ui.autofill.PasswordAutofillActivity

class FillResponseBuilder(private val datasetFactory: DatasetFactory) {

    private var fillResponseBuilder = FillResponse.Builder()

    fun addDataset(
        autofillId: AutofillId,
        promptMessage: String,
        autofillValue: String,
        packageName: String,
        inlinePresentationSpec: InlinePresentationSpec? = null,
    ): FillResponseBuilder {
        fillResponseBuilder.addDataset(
            datasetFactory.create(
                autofillId,
                promptMessage,
                autofillValue,
                packageName,
                inlinePresentationSpec
            )
        )

        return this
    }

    fun addDatasetWithItemsAndInAppSelection(
        autofillId: AutofillId,
        packageName: String,
        items: List<AutofillPasswordDetails>,
        context: Context,
        assistStructure: AssistStructure,
        parsedStructure: ParsedStructure,
        inlinePresentationSpec: InlinePresentationSpec? = null
    ): FillResponseBuilder {
        val pendingIntent = PendingIntent.getActivity(
            context,
            PENDING_INTENT_REQUEST_ID,
            PasswordAutofillActivity.newIntent(
                context = context,
                assistStructure = assistStructure,
                packageName = parsedStructure.packageName
            ),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )

        items.forEach {
            fillResponseBuilder.addDataset(
                datasetFactory.create(
                    autofillId = autofillId,
                    packageName = packageName,
                    promptMessage = it.platformName,
                    autofillValue = it.password,
                    inlinePresentationSpec = inlinePresentationSpec ?: InlinePresentationSpec.Builder(Size(100, 30), Size(1000, 30)).build(),
                )
            )
        }

        val promptMessage =
            context.getString(R.string.autofill_password_authentication_prompt_message)

        fillResponseBuilder.addDataset(
            datasetFactory.createAuthenticationDataset(
                autofillId = autofillId,
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
        fillRequest: FillRequest,
    ): FillResponseBuilder {
        val pendingIntent = PendingIntent.getActivity(
            context,
            PENDING_INTENT_REQUEST_ID,
            PasswordAutofillActivity.newIntent(
                context = context,
                assistStructure = assistStructure,
                packageName = parsedStructure.packageName
            ),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
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
                inlinePresentationSpec = fillRequest.inlineSuggestionsRequest?.inlinePresentationSpecs?.first() ?: InlinePresentationSpec.Builder(Size(100, 30), Size(1000, 30)).build(),
            )
        )

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
