package com.mateuszholik.passwordgenerator.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.SaveIfShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.domain.usecase.SavePasswordValidityValueUseCase
import com.mateuszholik.domain.usecase.ShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.listeners.OnSwitchChangedValueListener
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class SettingsViewModel(
    private val saveIfShouldUseBiometricAuthenticationUseCase: SaveIfShouldUseBiometricAuthenticationUseCase,
    private val shouldUseBiometricAuthenticationUseCase: ShouldUseBiometricAuthenticationUseCase,
    private val savePasswordValidityValueUseCase: SavePasswordValidityValueUseCase,
    private val textProvider: TextProvider
) : BaseViewModel(), OnSwitchChangedValueListener {

    private val _shouldUseBiometricAuthentication = MutableLiveData<Boolean>()
    val shouldUseBiometricAuthentication: LiveData<Boolean>
        get() = _shouldUseBiometricAuthentication

    init {
        getIfShouldUseBiometricAuthentication()
    }

    override fun onValueChanged(isChecked: Boolean) {
        saveIfShouldUseBiometricAuthenticationUseCase(isChecked)
            .subscribeWithObserveOnMainThread(
                doOnError = {
                    Timber.e(it)
                    _errorOccurred.postValue(
                        textProvider.provide(MessageType.SAVE_BIOMETRIC_AUTH_OPTION_ERROR)
                    )
                }
            )
            .addTo(compositeDisposable)
    }

    fun savePasswordValidity(numOfDays: Long) {
        savePasswordValidityValueUseCase(numOfDays)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {},
                doOnError = {
                    _errorOccurred.postValue(
                        textProvider.provide(MessageType.SAVE_PASSWORD_VALIDITY_OPTION_ERROR)
                    )
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun getIfShouldUseBiometricAuthentication() {
        shouldUseBiometricAuthenticationUseCase()
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    _shouldUseBiometricAuthentication.postValue(it)
                },
                doOnError = {
                    Timber.e(it)
                    _errorOccurred.postValue(
                        textProvider.provide(MessageType.GET_BIOMETRIC_AUTH_OPTION_ERROR)
                    )
                }
            )
    }
}
