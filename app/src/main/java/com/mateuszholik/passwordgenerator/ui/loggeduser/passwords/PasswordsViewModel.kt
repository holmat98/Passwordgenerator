package com.mateuszholik.passwordgenerator.ui.loggeduser.passwords

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.managers.PasswordScoreManager
import com.mateuszholik.domain.usecase.GetPasswordsUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class PasswordsViewModel(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val passwordsScoreManager: PasswordScoreManager
) : BaseViewModel() {

    private val _passwords = MutableLiveData<List<Password>>()
    val passwords: LiveData<List<Password>>
        get() = _passwords

    init {
        getAllPasswords()
    }

    fun getAllPasswords() {
        getPasswordsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _passwords.postValue(it) },
                {
                    _errorOccurred.postValue(true)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }

    fun getPasswordScore(password: String) = passwordsScoreManager.getScore(password)
}