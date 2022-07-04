package com.mateuszholik.passwordgenerator.ui.passwords

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.providers.PasswordScoreProvider
import com.mateuszholik.domain.usecase.GetPasswordsUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class PasswordsViewModel(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val passwordsScoreProvider: PasswordScoreProvider
) : BaseViewModel() {

    private val _passwords = MutableLiveData<List<Password>>()
    val passwords: LiveData<List<Password>>
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

    fun getPasswordScore(password: String) = passwordsScoreProvider.getScore(password)
}