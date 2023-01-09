package com.mateuszholik.passwordgenerator.ui.passwords

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.models.PasswordType
import com.mateuszholik.domain.usecase.GetPasswordsUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class PasswordsViewModel(
    private val getPasswordsUseCase: GetPasswordsUseCase
) : BaseViewModel() {

    private val _passwords = MutableLiveData<List<PasswordType>>()
    val passwords: LiveData<List<PasswordType>>
        get() = _passwords

    private val _isProgressBarVisible = MutableLiveData(true)
    val isProgressBarVisible: LiveData<Boolean>
        get() = _isProgressBarVisible

    init {
        getAllPasswords()
    }

    fun getAllPasswords() {
        getPasswordsUseCase()
            .doOnSubscribe { _isProgressBarVisible.postValue(true) }
            .doFinally { _isProgressBarVisible.postValue(false) }
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { _passwords.postValue(it) },
                doOnError = {
                    _errorOccurred.postValue(R.string.passwords_get_passwords_error)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }
}