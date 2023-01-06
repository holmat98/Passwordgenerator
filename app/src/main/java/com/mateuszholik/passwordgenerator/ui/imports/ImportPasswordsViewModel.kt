package com.mateuszholik.passwordgenerator.ui.imports

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.models.ImportType
import com.mateuszholik.domain.usecase.ImportPasswordsUseCase
import com.mateuszholik.passwordgenerator.extensions.addSources
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordgenerator.utils.Constants
import timber.log.Timber

class ImportPasswordsViewModel(
    private val importPasswordsUseCase: ImportPasswordsUseCase
) : BaseViewModel() {

    private val _importResult = MutableLiveData<Boolean>()
    val importResult: LiveData<Boolean>
        get() = _importResult

    val areImportedPasswordsEncrypted = MutableLiveData(false)
    val encryptionPassword = MutableLiveData(Constants.EMPTY_STRING)

    val isButtonEnabled = MediatorLiveData<Boolean>().addSources(
        areImportedPasswordsEncrypted,
        encryptionPassword
    ) { areInputsFilledCorrectly() }

    fun importPasswords(uri: Uri) {
        val importType = getImportType(uri)

        importPasswordsUseCase(importType)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { _importResult.postValue(true) },
                doOnError = {
                    Timber.e("Error occurred during password import", it)
                    _importResult.postValue(false)
                }
            )
    }

    private fun getImportType(uri: Uri): ImportType =
        if (areImportedPasswordsEncrypted.value == true) {
            ImportType.EncryptedImport(uri, encryptionPassword.value.orEmpty())
        } else {
            ImportType.Import(uri)
        }

    private fun areInputsFilledCorrectly(): Boolean =
        areImportedPasswordsEncrypted.value == false ||
                (areImportedPasswordsEncrypted.value == true && encryptionPassword.value?.isNotEmpty() == true)
}