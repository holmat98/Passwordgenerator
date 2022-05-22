package com.mateuszholik.passwordgenerator.ui.authentication.createpin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.CreatePinUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class CreatePinViewModel(
    private val createPinUseCase: CreatePinUseCase
) : BaseViewModel() {

    private val _pinCreateSuccess = MutableLiveData<Boolean>()
    val pinCreateSuccess: LiveData<Boolean>
        get() = _pinCreateSuccess

    fun createPin(pin: String) {
        createPinUseCase(pin)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    _pinCreateSuccess.postValue(true)
                },
                doOnError = {
                    _errorOccurred.postValue(R.string.create_pin_error)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }
}