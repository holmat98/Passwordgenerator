package com.mateuszholik.passwordgenerator.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.models.PinState
import com.mateuszholik.domain.usecase.IsPinCorrectUseCase
import com.mateuszholik.domain.usecase.ShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class LogInViewModel(
    private val isPinCorrectUseCase: IsPinCorrectUseCase,
    private val shouldUseBiometricAuthenticationUseCase: ShouldUseBiometricAuthenticationUseCase,
    private val textProvider: TextProvider
) : BaseViewModel() {

    private val _wrongPin = MutableLiveData<String>()
    val wrongPin: LiveData<String>
        get() = _wrongPin

    private val _shouldUseBiometricAuthentication = MutableLiveData<Boolean>()
    val shouldUseBiometricAuthentication: LiveData<Boolean>
        get() = _shouldUseBiometricAuthentication

    fun logIn(pin: String) {
        isPinCorrectUseCase(pin)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { managePinState(it) },
                doOnError = {
                    _errorOccurred.postValue(textProvider.provide(MessageType.LOGIN_ERROR))
                    Timber.e("Error during logging in", it)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun managePinState(pinState: PinState) =
        when (pinState) {
            PinState.CORRECT -> getIfShouldUseBiometricAuth()
            PinState.WRONG -> _wrongPin.postValue(textProvider.provide(MessageType.LOGIN_WRONG_PIN_ERROR))
        }

    private fun getIfShouldUseBiometricAuth() {
        shouldUseBiometricAuthenticationUseCase()
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { _shouldUseBiometricAuthentication.postValue(it) },
                doOnError = {
                    _errorOccurred.postValue(textProvider.provide(MessageType.DEFAULT_ERROR))
                    Timber.e("Error during getting if should use biometric auth", it)
                }
            )
            .addTo(compositeDisposable)
    }
}
