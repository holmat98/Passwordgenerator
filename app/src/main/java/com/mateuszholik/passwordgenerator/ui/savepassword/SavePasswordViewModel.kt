package com.mateuszholik.passwordgenerator.ui.savepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.passwordgenerator.utils.Constants.EMPTY_STRING
import com.mateuszholik.domain.models.NewPassword
import com.mateuszholik.domain.usecase.SavePasswordUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class SavePasswordViewModel(
    generatedPassword: String?,
    private val savePasswordUseCase: SavePasswordUseCase
) : BaseViewModel() {

    private val _savedPassword = MutableLiveData<Int>()
    val savedPassword: LiveData<Int>
        get() = _savedPassword

    val platformName = MutableLiveData(EMPTY_STRING)
    val password = MutableLiveData(EMPTY_STRING)

    init {
        password.postValue(generatedPassword)
    }

    fun savePassword() {
        val newPassword = NewPassword(
            platformName = platformName.value ?: EMPTY_STRING,
            password = password.value ?: EMPTY_STRING
        )
        savePasswordUseCase(newPassword)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { _savedPassword.postValue(R.string.save_password_saved) },
                doOnError = {
                    _errorOccurred.postValue(R.string.save_password_error)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }
}