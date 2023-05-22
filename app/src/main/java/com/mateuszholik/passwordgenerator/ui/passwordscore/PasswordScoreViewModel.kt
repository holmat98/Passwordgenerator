package com.mateuszholik.passwordgenerator.ui.passwordscore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.GetPasswordValidationResultUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import timber.log.Timber

class PasswordScoreViewModel(
    private val password: String,
    private val validatePasswordUseCase: GetPasswordValidationResultUseCase,
    private val textProvider: TextProvider,
) : BaseViewModel() {

    private val _passwordScore = MutableLiveData(0)
    val passwordScore: LiveData<Int>
        get() = _passwordScore

    private val _validationResult = MutableLiveData<List<PasswordValidationResult>>()
    val validationResult: LiveData<List<PasswordValidationResult>>
        get() = _validationResult

    init {
        validatePassword()
    }

    private fun validatePassword() {
        validatePasswordUseCase(password)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { validationResult ->
                    _validationResult.value = validationResult

                    val score = validationResult.sumOf { it.score }
                    val maxScore = validationResult.sumOf { it.maxScore }
                    _passwordScore.value = ((score.toFloat() / maxScore.toFloat()) * 100).toInt()
                },
                doOnError = {
                    Timber.e(it, "Error occurred during password validation")
                    _errorOccurred.value = textProvider.provide(MessageType.VALIDATION_ERROR)
                }
            )
            .addTo(compositeDisposable)
    }
}
