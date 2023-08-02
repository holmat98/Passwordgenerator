package com.mateuszholik.passwordgenerator.autofill.services

import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import com.mateuszholik.passwordgenerator.autofill.builders.FillResponseBuilder
import com.mateuszholik.passwordgenerator.autofill.parsers.StructureParser
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CustomAutofillService : AutofillService(), KoinComponent {

    private val fillResponseBuilder: FillResponseBuilder by inject()
    private val structureParser: StructureParser by inject()

    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal,
        callback: FillCallback,
    ) {
        val fillContext = request.fillContexts.lastOrNull() ?: return
        val structure = fillContext.structure

        val parsedStructure = structureParser.parse(structure)

        if (parsedStructure == null || parsedStructure.packageName == packageName) {
            callback.onSuccess(null)
        } else {
            val fillResponse = fillResponseBuilder
                .addSelectPasswordDialog(
                    context = this.applicationContext,
                    autofillIds = arrayOf(parsedStructure.autofillId)
                )
                .build()

            callback.onSuccess(fillResponse)
        }
    }

    override fun onSaveRequest(saveRequest: SaveRequest, saveCallback: SaveCallback) {
        TODO("Not yet implemented")
    }
}
