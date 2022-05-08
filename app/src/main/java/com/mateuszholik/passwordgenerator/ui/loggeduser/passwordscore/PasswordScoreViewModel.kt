package com.mateuszholik.passwordgenerator.ui.loggeduser.passwordscore

import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.CalculatePasswordScoreUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class PasswordScoreViewModel(
    private val password: String,
    private val calculatePasswordScoreUseCase: CalculatePasswordScoreUseCase
) : BaseViewModel() {

    val passwordScore = MutableLiveData<Int>()

    init {
        calculatePasswordScore()
    }

    private fun calculatePasswordScore() {
        calculatePasswordScoreUseCase(password)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { passwordScore.postValue(it) },
                {
                    _errorOccurred.postValue(true)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }
}