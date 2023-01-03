package com.mateuszholik.passwordgenerator.ui.imports

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordgenerator.utils.Constants

class ImportPasswordsViewModel : BaseViewModel() {

    val areImportedPasswordsEncrypted = MutableLiveData(false)
    val encryptionPassword = MutableLiveData(Constants.EMPTY_STRING)

    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(areImportedPasswordsEncrypted) {
            value = areInputsFilledCorrectly()
        }
        addSource(encryptionPassword) {
            value = areInputsFilledCorrectly()
        }
    }

    fun importPasswords() {}

    private fun areInputsFilledCorrectly(): Boolean =
        areImportedPasswordsEncrypted.value == false ||
                (areImportedPasswordsEncrypted.value == true && encryptionPassword.value?.isNotEmpty() == true)
}