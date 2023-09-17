package com.mateuszholik.passwordgenerator.autofill.services

import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import com.mateuszholik.domain.usecase.GetMatchingPasswordsForPackageNameUseCase
import com.mateuszholik.passwordgenerator.autofill.builders.FillResponseBuilder
import com.mateuszholik.passwordgenerator.autofill.parsers.StructureParser
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class CustomAutofillService : AutofillService(), KoinComponent {

    private val fillResponseBuilder: FillResponseBuilder by inject()
    private val structureParser: StructureParser by inject()
    private val getMatchingPasswordsForPackageNameUseCase: GetMatchingPasswordsForPackageNameUseCase by inject()
    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

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
            getMatchingPasswordsForPackageNameUseCase(parsedStructure.packageName)
                .subscribeWithObserveOnMainThread(
                    doOnSuccess = {
                        val fillResponseBuilder = fillResponseBuilder

                        val inlinePresentationSpec =
                            request.inlineSuggestionsRequest?.inlinePresentationSpecs?.firstOrNull()

                        if (it.isNotEmpty()) {
                            fillResponseBuilder.addDatasetForSuggestedAutofillItems(
                                packageName = packageName,
                                items = it,
                                parsedStructure = parsedStructure,
                                inlinePresentationSpec = inlinePresentationSpec
                            )
                        }

                        fillResponseBuilder.addSelectPasswordDialog(
                            context = this.applicationContext,
                            parsedStructure = parsedStructure,
                            assistStructure = structure,
                            inlinePresentationSpec = inlinePresentationSpec
                        )

                        callback.onSuccess(fillResponseBuilder.build())
                    },
                    doOnError = {
                        Timber.e(it, "Error while getting matching passwords for autofill")
                        callback.onSuccess(null)
                    }
                )
        }
    }

    override fun onSaveRequest(saveRequest: SaveRequest, saveCallback: SaveCallback) {
        TODO("Not yet implemented")
    }
}
