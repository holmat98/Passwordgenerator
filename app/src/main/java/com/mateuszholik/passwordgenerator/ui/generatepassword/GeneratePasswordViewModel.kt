package com.mateuszholik.passwordgenerator.ui.generatepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.CreatePasswordUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class GeneratePasswordViewModel(
    private val createPasswordUseCase: CreatePasswordUseCase
) : BaseViewModel() {

    private val _generatedPassword = MutableLiveData<String>()
    val generatedPassword: LiveData<String>
        get() = _generatedPassword

    val passwordLength = MutableLiveData(DEFAULT_PASSWORD_LENGTH)

    fun createPassword() {
        val length = passwordLength.value ?: DEFAULT_PASSWORD_LENGTH
        createPasswordUseCase(length)
            .subscribeWithObserveOnMainThread(
                scheduler = Schedulers.computation(),
                doOnSuccess = { _generatedPassword.postValue(it) },
                doOnError = {
                    Timber.e("Error during generating password", it)
                    _errorOccurred.postValue(R.string.generate_password_error)
                }
            )
            .addTo(compositeDisposable)
    }
    private companion object {
        const val DEFAULT_PASSWORD_LENGTH = 8
    }
}
