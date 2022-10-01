package com.mateuszholik.passwordgenerator.ui.passworddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.usecase.CalculatePasswordScoreUseCase
import com.mateuszholik.domain.usecase.DeletePasswordUseCase
import com.mateuszholik.passwordvalidation.usecases.ValidatePasswordUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.getDiffFromNowInMilliseconds
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.managers.ClipboardManager
import com.mateuszholik.passwordgenerator.schedulers.WorkScheduler
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class PasswordDetailsViewModel(
    private val password: Password,
    private val calculatePasswordScoreUseCase: CalculatePasswordScoreUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val clipboardManager: ClipboardManager,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    val workScheduler: WorkScheduler
) : BaseViewModel() {

    private val _passwordDeletedSuccessfully = MutableLiveData<Boolean>()
    val passwordDeletedSuccessfully: LiveData<Boolean>
        get() = _passwordDeletedSuccessfully

    private val _passwordScore = MutableLiveData<Int>()
    val passwordScore: LiveData<Int>
        get() = _passwordScore

    private val _passwordValidationResult = MutableLiveData<PasswordValidationResult>()
    val passwordValidationResult: LiveData<PasswordValidationResult>
        get() = _passwordValidationResult

    init {
        getPasswordScore()
        validatePassword()
    }

    private fun getPasswordScore() {
        calculatePasswordScoreUseCase(password.password)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { _passwordScore.postValue(it) },
                doOnError = {
                    Timber.e(it)
                    _errorOccurred.postValue(R.string.password_details_password_score_error)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun validatePassword() {
        validatePasswordUseCase(password.password)
            .subscribeWithObserveOnMainThread(
                doOnNext = { _passwordValidationResult.value = it },
                doOnSuccess = { Timber.i("Successfully validated password") },
                doOnError = {
                    Timber.e(it, "Error during password validation")
                    _errorOccurred.value = R.string.password_validation_result_error
                }
            )
            .addTo(compositeDisposable)
    }

    fun copyPasswordToClipboard() =
        clipboardManager.copyToClipboard(password.platformName, password.password)

    fun deletePassword() {
        deletePasswordUseCase(password.id)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    workScheduler.cancelWorker(password.id)
                    _passwordDeletedSuccessfully.postValue(true)
                },
                doOnError = {
                    Timber.e(it)
                    _errorOccurred.postValue(R.string.password_details_delete_password_error)
                }
            )
            .addTo(compositeDisposable)
    }

    fun scheduleNotification() {
        workScheduler.schedule(password, password.expiringDate.getDiffFromNowInMilliseconds())
    }
}