package com.mateuszholik.passwordgenerator.ui.editpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.models.UpdatedPassword
import com.mateuszholik.domain.usecase.GetPasswordUseCase
import com.mateuszholik.domain.usecase.UpdatePasswordUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.expirationDate
import com.mateuszholik.passwordgenerator.extensions.getDiffFromNowInMilliseconds
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.schedulers.WorkScheduler
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordgenerator.utils.Constants.EMPTY_STRING
import timber.log.Timber

class EditPasswordViewModel(
    private val passwordId: Long,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val textProvider: TextProvider,
    private val workScheduler: WorkScheduler,
    private val getPasswordUseCase: GetPasswordUseCase,
) : BaseViewModel() {

    val newPlatformNameValue = MutableLiveData(EMPTY_STRING)
    val newPasswordValue = MutableLiveData(EMPTY_STRING)
    val newWebsiteValue = MutableLiveData(EMPTY_STRING)
    val isExpiring = MutableLiveData(true)

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
        getPasswordUseCase(passwordId)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    newPlatformNameValue.postValue(it.platformName)
                    newPasswordValue.postValue(it.password)
                    newWebsiteValue.postValue(it.website)
                },
                doOnError = {
                    Timber.e(it, "Error occurred while getting the password details")
                    _errorOccurred.postValue(textProvider.provide(MessageType.GET_PASSWORDS_ERROR))
                }
            )
    }

    fun editPassword() {
        val updatedPassword = UpdatedPassword(
            id = passwordId,
            platformName = newPlatformNameValue.value.orEmpty(),
            password = newPasswordValue.value.orEmpty(),
            website = newWebsiteValue.value.orEmpty(),
            isExpiring = isExpiring.value ?: false
        )
        updatePasswordUseCase(updatedPassword)
            .andThen(getPasswordUseCase(passwordId))
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    _passwordEditedCorrectly.postValue(true)
                    it.passwordValidity.expirationDate?.let { expirationDate ->
                        workScheduler.schedule(
                            passwordId,
                            expirationDate.getDiffFromNowInMilliseconds()
                        )
                    }
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
