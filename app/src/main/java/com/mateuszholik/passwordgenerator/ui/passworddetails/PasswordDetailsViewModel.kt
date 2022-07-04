package com.mateuszholik.passwordgenerator.ui.passworddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.usecase.CalculatePasswordScoreUseCase
import com.mateuszholik.domain.usecase.DeletePasswordUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.getDiffFromNowInMilliseconds
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.managers.ClipboardManager
import com.mateuszholik.passwordgenerator.schedulers.WorkScheduler
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class PasswordDetailsViewModel(
    private val password: Password,
    private val calculatePasswordScoreUseCase: CalculatePasswordScoreUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val clipboardManager: ClipboardManager,
    private val workScheduler: WorkScheduler
) : BaseViewModel() {

    private val _passwordDeletedSuccessfully = MutableLiveData<Boolean>()
    val passwordDeletedSuccessfully: LiveData<Boolean>
        get() = _passwordDeletedSuccessfully

    private val _passwordScore = MutableLiveData<Int>()
    val passwordScore: LiveData<Int>
        get() = _passwordScore

    init {
        getPasswordScore()
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