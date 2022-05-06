package com.mateuszholik.passwordgenerator.ui.loggeduser.passwords

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.managers.PasswordScoreManager
import com.mateuszholik.domain.models.State
import com.mateuszholik.domain.usecase.GetPasswordsUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class PasswordsViewModel(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val passwordsScoreManager: PasswordScoreManager
) : ViewModel() {

    private val _passwords = MutableLiveData<List<Password>>()
    val passwords: LiveData<List<Password>>
        get() = _passwords

    private val _errorOccurred = MutableLiveData<Boolean>()
    val errorOccurred: LiveData<Boolean>
        get() = _errorOccurred

    init {
        getAllPasswords()
    }

    private fun getAllPasswords() {
        getPasswordsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        is State.Success -> _passwords.postValue(it.data)
                        else -> _errorOccurred.postValue(true)
                    }
                },
                {
                    Log.d("TEST", it.localizedMessage)
                }
            )
    }

    fun getPasswordScore(password: String) = passwordsScoreManager.getScore(password)
}