package com.mateuszholik.passwordgenerator.ui.loggeduser.editpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.usecase.UpdatePasswordUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordgenerator.utils.Constants.EMPTY_STRING
import timber.log.Timber

class EditPasswordViewModel(
    private val password: Password,
    private val updatePasswordUseCase: UpdatePasswordUseCase
) : BaseViewModel() {

    val newPlatformNameValue = MutableLiveData<String>()
    val newPasswordValue = MutableLiveData<String>()

    private val _passwordEditedCorrectly = MutableLiveData<Boolean>()
    val passwordEditedCorrectly: LiveData<Boolean>
        get() = _passwordEditedCorrectly

    init {
        with(password) {
            newPlatformNameValue.postValue(platformName)
            newPasswordValue.postValue(password)
        }
    }

    fun editPassword() {
        val editedPassword = Password(
            id = password.id,
            platformName = newPlatformNameValue.value ?: EMPTY_STRING,
            password = newPasswordValue.value ?: EMPTY_STRING,
            expiringDate = password.expiringDate
        )
        updatePasswordUseCase(editedPassword)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    _passwordEditedCorrectly.postValue(true)
                },
                doOnError = {
                    Timber.e(it)
                    _errorOccurred.postValue(true)
                }
            )
            .addTo(compositeDisposable)
    }
}