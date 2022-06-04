package com.mateuszholik.passwordgenerator.ui.passwordscore

import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.CalculatePasswordScoreUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
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
            .subscribeWithObserveOnMainThread(
                scheduler = Schedulers.computation(),
                doOnSuccess = { passwordScore.postValue(it) },
                doOnError = {
                    _errorOccurred.postValue(R.string.password_details_password_score_error)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }
}