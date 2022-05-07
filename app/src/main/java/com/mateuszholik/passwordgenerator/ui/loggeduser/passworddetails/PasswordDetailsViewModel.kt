package com.mateuszholik.passwordgenerator.ui.loggeduser.passworddetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.usecase.CalculatePasswordScoreUseCase
import com.mateuszholik.passwordgenerator.managers.ClipboardManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class PasswordDetailsViewModel(
    private val password: Password,
    private val calculatePasswordScoreUseCase: CalculatePasswordScoreUseCase,
    private val clipboardManager: ClipboardManager
) : ViewModel() {

    private val _passwordScore = MutableLiveData<Int>()
    val passwordScore: LiveData<Int>
        get() = _passwordScore

    init {
        getPasswordScore()
    }

    private fun getPasswordScore() {
        calculatePasswordScoreUseCase(password.password)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _passwordScore.postValue(it) },
                { Log.d("TEST", "${it.message}") }
            )
    }

    fun copyPasswordToClipboard() =
        clipboardManager.copyToClipboard(password.platformName, password.password)
}