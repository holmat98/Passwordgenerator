package com.mateuszholik.passwordgenerator.ui.passwords

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.domain.usecase.GetPasswordsUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class PasswordsViewModel(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val textProvider: TextProvider
) : BaseViewModel() {

    private val _passwords = MutableLiveData<List<PasswordInfo>>()
    val passwords: LiveData<List<PasswordInfo>>
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
                    _errorOccurred.postValue(textProvider.provide(MessageType.GET_PASSWORDS_ERROR))
                    Timber.e(it, "Error while getting passwords")
                }
            )
            .addTo(compositeDisposable)
    }
}
