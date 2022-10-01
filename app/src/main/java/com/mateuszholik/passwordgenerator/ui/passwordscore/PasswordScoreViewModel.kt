package com.mateuszholik.passwordgenerator.ui.passwordscore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.usecase.CalculatePasswordScoreUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.usecases.ValidatePasswordUseCase
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class PasswordScoreViewModel(
    private val password: String,
    private val calculatePasswordScoreUseCase: CalculatePasswordScoreUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : BaseViewModel() {

    val passwordScore = MutableLiveData<Int>()

    private val _validationResult = MutableLiveData<PasswordValidationResult>()
    val validationResult: LiveData<PasswordValidationResult>
        get() = _validationResult

    init {
        calculatePasswordScore()
        validatePassword()
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

    private fun validatePassword() {
        validatePasswordUseCase(password)
            .subscribeWithObserveOnMainThread(
                doOnNext = { _validationResult.value = it },
                doOnSuccess = { Timber.i("Password validation has finished") },
                doOnError = {
                    Timber.e(it, "Error occurred during password validation")
                    _errorOccurred.value = R.string.password_validation_result_error
                }
            )
    }
}