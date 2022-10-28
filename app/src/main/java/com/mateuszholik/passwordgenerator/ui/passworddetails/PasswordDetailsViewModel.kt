package com.mateuszholik.passwordgenerator.ui.passworddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.usecase.DeletePasswordUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.getDiffFromNowInMilliseconds
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.managers.ClipboardManager
import com.mateuszholik.passwordgenerator.schedulers.WorkScheduler
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.usecases.ValidatePasswordUseCase
import io.reactivex.rxjava3.core.Completable
import timber.log.Timber

class PasswordDetailsViewModel(
    private val password: Password,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val clipboardManager: ClipboardManager,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    val workScheduler: WorkScheduler
) : BaseViewModel() {

    private var currentScore = 0f
    private var currentMaxScore = 0f

    private val _passwordDeletedSuccessfully = MutableLiveData<Boolean>()
    val passwordDeletedSuccessfully: LiveData<Boolean>
        get() = _passwordDeletedSuccessfully

    private val _passwordScore = MutableLiveData(0)
    val passwordScore: LiveData<Int>
        get() = _passwordScore

    private val _passwordValidationResult = MutableLiveData<PasswordValidationResult>()
    val passwordValidationResult: LiveData<PasswordValidationResult>
        get() = _passwordValidationResult

    init {
        validatePassword()
    }

    private fun validatePassword() {
        validatePasswordUseCase(password.password)
            .subscribeWithObserveOnMainThread(
                doOnNext = {
                    _passwordValidationResult.value = it
                    currentScore += it.score
                    currentMaxScore += it.maxScore
                    _passwordScore.value = ((currentScore / currentMaxScore) * 100).toInt()
                },
                doOnSuccess = { Timber.i("Successfully validated password") },
                doOnError = {
                    Timber.e(it, "Error during password validation")
                    _errorOccurred.value = R.string.password_validation_error
                }
            )
            .addTo(compositeDisposable)
    }

    fun copyPasswordToClipboard() =
        clipboardManager.copyToClipboard(password.platformName, password.password)

    fun deletePassword() {
        deletePasswordUseCase(password.id)
            .andThen(
                Completable.fromAction {
                    workScheduler.cancelWorker(password.id)
                }
            )
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
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
        workScheduler.schedule(password.id, password.expiringDate.getDiffFromNowInMilliseconds())
    }
}