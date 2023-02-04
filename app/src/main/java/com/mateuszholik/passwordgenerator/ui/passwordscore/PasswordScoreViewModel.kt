package com.mateuszholik.passwordgenerator.ui.passwordscore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.usecases.ValidatePasswordUseCase
import timber.log.Timber

class PasswordScoreViewModel(
    private val password: String,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val textProvider: TextProvider
) : BaseViewModel() {

    private var currentScore = 0f
    private var currentMaxScore = 0f

    private val _passwordScore = MutableLiveData(0)
    val passwordScore: LiveData<Int>
        get() = _passwordScore

    private val _validationResult = MutableLiveData<PasswordValidationResult>()
    val validationResult: LiveData<PasswordValidationResult>
        get() = _validationResult

    init {
        validatePassword()
    }

    private fun validatePassword() {
        validatePasswordUseCase(password)
            .subscribeWithObserveOnMainThread(
                doOnNext = {
                    _validationResult.value = it
                    currentScore += it.score
                    currentMaxScore += it.maxScore
                    _passwordScore.value = ((currentScore / currentMaxScore) * 100).toInt()
                },
                doOnSuccess = { Timber.i("Password validation has finished") },
                doOnError = {
                    Timber.e(it, "Error occurred during password validation")
                    _errorOccurred.value = textProvider.provide(MessageType.VALIDATION_ERROR)
                }
            )
            .addTo(compositeDisposable)
    }
}
