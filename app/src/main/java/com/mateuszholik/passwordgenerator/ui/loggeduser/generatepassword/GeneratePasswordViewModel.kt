package com.mateuszholik.passwordgenerator.ui.loggeduser.generatepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.constants.Constants.EMPTY_STRING
import com.mateuszholik.domain.usecase.CreatePasswordUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.listeners.OnValueChangedListener
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class GeneratePasswordViewModel(
    private val createPasswordUseCase: CreatePasswordUseCase
) : BaseViewModel(), OnValueChangedListener {

    private val _generatedPassword = MutableLiveData(EMPTY_STRING)
    val generatedPassword: LiveData<String>
        get() = _generatedPassword

    private var passwordLength: Int = 8

    fun createPassword() {
        createPasswordUseCase(passwordLength)
            .subscribeWithObserveOnMainThread(
                scheduler = Schedulers.computation(),
                doOnSuccess = { _generatedPassword.postValue(it) },
                doOnError = {
                    Timber.e(it)
                    _errorOccurred.postValue(R.string.generate_password_error)
                }
            )
            .addTo(compositeDisposable)
    }

    override fun onValueChanged(value: Float) {
        passwordLength = value.toInt()
    }
}