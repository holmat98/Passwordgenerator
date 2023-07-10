package com.mateuszholik.passwordgenerator.ui.editpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.domain.models.UpdatedPassword
import com.mateuszholik.domain.usecase.UpdatePasswordUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.schedulers.WorkScheduler
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordgenerator.utils.Constants.EMPTY_STRING
import timber.log.Timber

class EditPasswordViewModel(
    private val passwordInfo: PasswordInfo,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val textProvider: TextProvider,
    private val workScheduler: WorkScheduler
) : BaseViewModel() {

    val newPlatformNameValue = MutableLiveData<String>()
    val newPasswordValue = MutableLiveData<String>()

    private val _passwordEditedCorrectly = MutableLiveData<Boolean>()
    val passwordEditedCorrectly: LiveData<Boolean>
        get() = _passwordEditedCorrectly

    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        value = false
        addSource(newPasswordValue) {
            value = areInputsNotEmpty()
        }
        addSource(newPlatformNameValue) {
            value = areInputsNotEmpty()
        }
    }

    init {
        with(passwordInfo) {
            newPlatformNameValue.postValue(platformName)
//            newPasswordValue.postValue(password)
        }
    }

    fun editPassword() {
        val updatedPassword = UpdatedPassword(
            id = passwordInfo.id,
            platformName = newPlatformNameValue.value ?: EMPTY_STRING,
            password = newPasswordValue.value ?: EMPTY_STRING,
        )
        updatePasswordUseCase(updatedPassword)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    _passwordEditedCorrectly.postValue(true)
                    workScheduler.cancelWorker(passwordInfo.id)
                },
                doOnError = {
                    Timber.e(it)
                    _errorOccurred.postValue(textProvider.provide(MessageType.PASSWORD_EDITION_ERROR))
                }
            )
            .addTo(compositeDisposable)
    }

    private fun areInputsNotEmpty(): Boolean =
        newPasswordValue.value?.isNotEmpty() ?: false &&
                newPlatformNameValue.value?.isNotEmpty() ?: false
}
