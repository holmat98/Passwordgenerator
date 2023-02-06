package com.mateuszholik.passwordgenerator.ui.createpin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.IsPinCorrectToSaveUseCase
import com.mateuszholik.domain.usecase.SavePinUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class CreatePinViewModel(
    private val isPinCorrectToSaveUseCase: IsPinCorrectToSaveUseCase,
    private val savePinUseCase: SavePinUseCase,
    private val textProvider: TextProvider
) : BaseViewModel() {

    private val _savedPinSuccessfully = MutableLiveData<Boolean>()
    val savedPinSuccessfully: LiveData<Boolean>
        get() = _savedPinSuccessfully

    private val _wrongPin = MutableLiveData<String>()
    val wrongPin: LiveData<String>
        get() = _wrongPin

    fun savePinIfCorrect(pin: String) {
        isPinCorrectToSaveUseCase(pin)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { isPinCorrectToSave ->
                    if (isPinCorrectToSave) {
                        savePin(pin)
                    } else {
                        _wrongPin.postValue(textProvider.provide(MessageType.CREATE_PIN_WRONG_PIN_ERROR))
                    }
                },
                doOnError = {
                    _errorOccurred.postValue(textProvider.provide(MessageType.CREATE_PIN_ERROR))
                    Timber.e("Error during checking if pin is correct", it)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun savePin(pin: String) {
        savePinUseCase(pin)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { _savedPinSuccessfully.postValue(true) },
                doOnError = {
                    _errorOccurred.postValue(textProvider.provide(MessageType.CREATE_PIN_ERROR))
                    Timber.e("Error during pin save", it)
                }
            )
            .addTo(compositeDisposable)
    }
}
