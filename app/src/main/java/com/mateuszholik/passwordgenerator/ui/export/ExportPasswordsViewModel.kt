package com.mateuszholik.passwordgenerator.ui.export

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.models.ExportType
import com.mateuszholik.domain.usecase.ExportPasswordsUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordgenerator.utils.Constants.EMPTY_STRING
import timber.log.Timber

class ExportPasswordsViewModel(
    private val exportPasswordsUseCase: ExportPasswordsUseCase
) : BaseViewModel() {

    private val _exportResult = MutableLiveData<Boolean>()
    val exportResult: LiveData<Boolean>
        get() = _exportResult

    val shouldExportedPasswordBeEncrypted = MutableLiveData(false)
    val encryptionPassword = MutableLiveData(EMPTY_STRING)

    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        value = false
        addSource(shouldExportedPasswordBeEncrypted) {
            value = areInputsFilledCorrectly()
        }
        addSource(encryptionPassword) {
            value = areInputsFilledCorrectly()
        }
    }

    fun exportPasswords() {
        val exportType = getExportType()

        exportPasswordsUseCase(exportType)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    _exportResult.postValue(true)
                },
                doOnError = {
                    _exportResult.postValue(false)
                    Timber.e("Error occurred during passwords export", it)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun getExportType(): ExportType =
        if (shouldExportedPasswordBeEncrypted.value == true) {
            ExportType.EncryptedExport(encryptionPassword.value ?: EMPTY_STRING)
        } else {
            ExportType.Export
        }

    private fun areInputsFilledCorrectly(): Boolean =
        shouldExportedPasswordBeEncrypted.value == false ||
                (shouldExportedPasswordBeEncrypted.value == true && encryptionPassword.value?.isNotEmpty() == true)
}