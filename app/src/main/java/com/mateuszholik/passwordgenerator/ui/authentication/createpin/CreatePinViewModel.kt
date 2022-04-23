package com.mateuszholik.passwordgenerator.ui.authentication.createpin

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszholik.domain.usecase.CreatePinUseCase
import com.mateuszholik.passwordgenerator.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CreatePinViewModel(
    private val createPinUseCase: CreatePinUseCase
) : ViewModel() {

    private val _pinCreateSuccess = MutableLiveData<Boolean>()
    val pinCreateSuccess: LiveData<Boolean>
        get() = _pinCreateSuccess

    private val _pinCreateError = MutableLiveData<Int>()
    val pinCreateError: LiveData<Int>
        get() = _pinCreateError

    fun createPin(pin: String) {
        createPinUseCase(pin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _pinCreateSuccess.postValue(true) },
                { _pinCreateError.postValue(R.string.error_message) }
            )
    }
}