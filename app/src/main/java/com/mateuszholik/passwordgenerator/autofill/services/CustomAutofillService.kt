package com.mateuszholik.passwordgenerator.autofill.services

import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import android.view.autofill.AutofillId
import com.mateuszholik.passwordgenerator.autofill.builders.FillResponseBuilder
import com.mateuszholik.passwordgenerator.autofill.parsers.StructureParser
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CustomAutofillService : AutofillService(), KoinComponent {

    private val fillResponseBuilder: FillResponseBuilder by inject()

    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal,
        callback: FillCallback,
    ) {
        val autofillIds = request.getAutoFillIds()

        if (autofillIds.isEmpty()) {
            callback.onSuccess(null)
        } else {
            val fillResponse = fillResponseBuilder
                .addSelectPasswordDialog(
                    context = this.applicationContext,
                    autofillIds = autofillIds
                )
                .build()

            callback.onSuccess(fillResponse)
        }
    }

    override fun onSaveRequest(saveRequest: SaveRequest, saveCallback: SaveCallback) {
        TODO("Not yet implemented")
    }

    private fun FillRequest.getAutoFillIds(): Array<AutofillId> {
        val fillContext = fillContexts[fillContexts.size - 1]
        val structure = fillContext.structure
        val parser = StructureParser(structure)
        parser.parse()

        return parser.autoFillIds.toTypedArray()
    }
}
