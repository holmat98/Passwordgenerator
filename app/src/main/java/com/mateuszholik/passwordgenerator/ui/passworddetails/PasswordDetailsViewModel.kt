package com.mateuszholik.passwordgenerator.ui.passworddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.usecase.DeletePasswordUseCase
import com.mateuszholik.domain.usecase.GetPasswordValidationResultUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.managers.ClipboardManager
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.schedulers.WorkScheduler
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import io.reactivex.rxjava3.core.Completable
import timber.log.Timber

class PasswordDetailsViewModel(
    private val password: Password,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val clipboardManager: ClipboardManager,
    private val validatePasswordUseCase: GetPasswordValidationResultUseCase,
    private val textProvider: TextProvider,
    private val workScheduler: WorkScheduler,
) : BaseViewModel() {

    private val _passwordDeletedSuccessfully = MutableLiveData<Boolean>()
    val passwordDeletedSuccessfully: LiveData<Boolean>
        get() = _passwordDeletedSuccessfully

    private val _passwordScore = MutableLiveData(0)
    val passwordScore: LiveData<Int>
        get() = _passwordScore

    private val _passwordValidationResult = MutableLiveData<List<PasswordValidationResult>>()
    val passwordValidationResult: LiveData<List<PasswordValidationResult>>
        get() = _passwordValidationResult

    init {
        validatePassword()
    }

    private fun validatePassword() {
        validatePasswordUseCase(password.password)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { validationResult ->
                    _passwordValidationResult.value = validationResult

                    val score = validationResult.sumOf { it.score }
                    val maxScore = validationResult.sumOf { it.maxScore }
                    _passwordScore.value = ((score.toFloat() / maxScore.toFloat()) * 100).toInt()
                },
                doOnError = {
                    Timber.e(it, "Error during password validation")
                    _errorOccurred.value = textProvider.provide(MessageType.VALIDATION_ERROR)
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
                    Timber.e(it, "Error while deleting the password")
                    _errorOccurred.postValue(textProvider.provide(MessageType.DELETE_PASSWORD_ERROR))
                }
            )
            .addTo(compositeDisposable)
    }
}
