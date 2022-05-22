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

    private val _logInResult = MutableLiveData<PinState>()
    val logInResult: LiveData<PinState>
        get() = _logInResult

    private val _shouldUseBiometricAuthentication = MutableLiveData<Boolean>()
    val shouldUseBiometricAuthentication: LiveData<Boolean>
        get() = _shouldUseBiometricAuthentication

    fun logIn(pin: String) {
        isPinCorrectUseCase(pin)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { _logInResult.postValue(it) },
                doOnError = {
                    _errorOccurred.postValue(R.string.log_in_error)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }

    fun getIfShouldUseBiometricAuth() {
        shouldUseBiometricAuthenticationUseCase()
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { _shouldUseBiometricAuthentication.postValue(it) },
                doOnError = {
                    _shouldUseBiometricAuthentication.postValue(false)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }
}