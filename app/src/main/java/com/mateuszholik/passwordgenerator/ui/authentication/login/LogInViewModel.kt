package com.mateuszholik.passwordgenerator.ui.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.models.PinState
import com.mateuszholik.domain.usecase.IsPinCorrectUseCase
import com.mateuszholik.domain.usecase.ShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class LogInViewModel(
    private val isPinCorrectUseCase: IsPinCorrectUseCase,
    private val shouldUseBiometricAuthenticationUseCase: ShouldUseBiometricAuthenticationUseCase
) : BaseViewModel() {

    private val _loginFailed = MutableLiveData<Int>()
    val loginFailed: LiveData<Int>
        get() = _loginFailed

    private val _shouldUseBiometricAuthentication = MutableLiveData<Boolean>()
    val shouldUseBiometricAuthentication: LiveData<Boolean>
        get() = _shouldUseBiometricAuthentication

    fun logIn(pin: String) {
        isPinCorrectUseCase(pin)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { managePinState(it) },
                doOnError = {
                    _errorOccurred.postValue(R.string.log_in_error)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun managePinState(pinState: PinState) =
        when(pinState) {
            PinState.CORRECT -> getIfShouldUseBiometricAuth()
            PinState.WRONG -> _loginFailed.postValue(R.string.log_in_wrong_pin)
            else -> _errorOccurred.postValue(R.string.error_message)
        }

    private fun getIfShouldUseBiometricAuth() {
        shouldUseBiometricAuthenticationUseCase()
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { _shouldUseBiometricAuthentication.postValue(it) },
                doOnError = {
                    _errorOccurred.postValue(R.string.error_message)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }
}