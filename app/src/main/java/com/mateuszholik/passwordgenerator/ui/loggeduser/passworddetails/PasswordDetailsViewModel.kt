package com.mateuszholik.passwordgenerator.ui.loggeduser.passworddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.usecase.CalculatePasswordScoreUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.managers.ClipboardManager
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class PasswordDetailsViewModel(
    private val password: Password,
    private val calculatePasswordScoreUseCase: CalculatePasswordScoreUseCase,
    private val clipboardManager: ClipboardManager
) : BaseViewModel() {

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
                { Timber.e(it) }
            )
            .addTo(compositeDisposable)
    }

    fun copyPasswordToClipboard() =
        clipboardManager.copyToClipboard(password.platformName, password.password)
}