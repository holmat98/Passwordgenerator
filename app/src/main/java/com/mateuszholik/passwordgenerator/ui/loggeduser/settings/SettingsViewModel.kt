package com.mateuszholik.passwordgenerator.ui.loggeduser.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.SaveIfShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.domain.usecase.ShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.listeners.OnSwitchChangedValueListener
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class SettingsViewModel(
    private val saveIfShouldUseBiometricAuthenticationUseCase: SaveIfShouldUseBiometricAuthenticationUseCase,
    private val shouldUseBiometricAuthenticationUseCase: ShouldUseBiometricAuthenticationUseCase
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
                    _errorOccurred.postValue(R.string.settings_error_on_saving_biometric_auth_setting)
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
                    _errorOccurred.postValue(R.string.settings_error_get_biometric_auth_setting)
                }
            )
    }
}