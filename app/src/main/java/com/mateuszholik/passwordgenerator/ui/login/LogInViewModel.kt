package com.mateuszholik.passwordgenerator.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszholik.domain.usecase.IsPinCorrectUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LogInViewModel(
    private val isPinCorrectUseCase: IsPinCorrectUseCase
) : ViewModel() {

    private val _logInSuccess = MutableLiveData<Boolean>()
    val logInSuccess: LiveData<Boolean>
        get() = _logInSuccess

    fun logIn(pin: String) {
        isPinCorrectUseCase(pin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _logInSuccess.postValue(it) },
                {
                    _logInSuccess.postValue(false)
                    Log.d(LOG_TAG, "${it.message}")
                }
            )
    }

    companion object {
        private const val LOG_TAG = "LogInViewModel"
    }
}