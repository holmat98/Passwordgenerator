package com.mateuszholik.passwordgenerator.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.models.PinState
import com.mateuszholik.domain.usecase.IsPinCreatedUseCase
import com.mateuszholik.domain.usecase.IsPinCorrectToSaveUseCase
import com.mateuszholik.domain.usecase.SavePinUseCase
import com.mateuszholik.domain.usecase.IsPinCorrectUseCase
import com.mateuszholik.domain.usecase.ShouldUseBiometricAuthenticationUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.mappers.StringResToStringMapper
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class LogInViewModel(
    private val isPinCreatedUseCase: IsPinCreatedUseCase,
    private val isPinCorrectToSaveUseCase: IsPinCorrectToSaveUseCase,
    private val savePinUseCase: SavePinUseCase,
    private val isPinCorrectUseCase: IsPinCorrectUseCase,
    private val shouldUseBiometricAuthenticationUseCase: ShouldUseBiometricAuthenticationUseCase,
    private val stringResToStringMapper: StringResToStringMapper
) : BaseViewModel() {

    private var isPinCreated: Boolean = false

    private val _headerText = MutableLiveData<String>()
    val headerText: LiveData<String>
        get() = _headerText

    private val _wrongPin = MutableLiveData<Int>()
    val wrongPin: LiveData<Int>
        get() = _wrongPin

    private val _shouldUseBiometricAuthentication = MutableLiveData<Boolean>()
    val shouldUseBiometricAuthentication: LiveData<Boolean>
        get() = _shouldUseBiometricAuthentication

    init {
        getIsPinCreated()
    }

    fun onSubmitButtonClicked(pin: String) {
        if (isPinCreated) {
            logIn(pin)
        } else {
            savePinIfCorrect(pin)
        }
    }

    private fun logIn(pin: String) {
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
        when (pinState) {
            PinState.CORRECT -> getIfShouldUseBiometricAuth()
            PinState.WRONG -> _wrongPin.postValue(R.string.log_in_wrong_pin)
            else -> _errorOccurred.postValue(R.string.error_message)
        }

    private fun savePinIfCorrect(pin: String) {
        isPinCorrectToSaveUseCase(pin)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { isPinCorrectToSave ->
                    if (isPinCorrectToSave) {
                        savePin(pin)
                    } else {
                        _wrongPin.postValue(R.string.create_pin_wrong_pin)
                    }
                },
                doOnError = {
                    _errorOccurred.postValue(R.string.create_pin_error)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun savePin(pin: String) {
        savePinUseCase(pin)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { getIfShouldUseBiometricAuth() },
                doOnError = {
                    _errorOccurred.postValue(R.string.create_pin_error)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
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

    private fun getIsPinCreated() {
        isPinCreatedUseCase()
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    isPinCreated = it
                    _headerText.postValue(
                        stringResToStringMapper.map(
                            if (it) {
                                R.string.log_in_header
                            } else {
                                R.string.create_pin_header
                            }
                        )
                    )
                },
                doOnError = {
                    isPinCreated = false
                    _headerText.postValue(stringResToStringMapper.map(R.string.create_pin_header))
                    Timber.i(it, "Cannot check if pin is created")
                }
            )
            .addTo(compositeDisposable)
    }
}