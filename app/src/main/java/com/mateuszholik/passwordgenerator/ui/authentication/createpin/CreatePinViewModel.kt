package com.mateuszholik.passwordgenerator.ui.authentication.createpin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.IsPinCorrectToSaveUseCase
import com.mateuszholik.domain.usecase.SavePinUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class CreatePinViewModel(
    private val isPinCorrectToSaveUseCase: IsPinCorrectToSaveUseCase,
    private val savePinUseCase: SavePinUseCase
) : BaseViewModel() {

    private val _wrongPin = MutableLiveData<Int>()
    val wrongPin: LiveData<Int>
        get() = _wrongPin

    private val _pinCreateSuccess = MutableLiveData<Boolean>()
    val pinCreateSuccess: LiveData<Boolean>
        get() = _pinCreateSuccess

    fun savePinIfCorrect(pin: String) {
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
                doOnSuccess = { _pinCreateSuccess.postValue(true) },
                doOnError = {
                    _errorOccurred.postValue(R.string.create_pin_error)
                    Timber.e(it)
                }
            )
    }
}