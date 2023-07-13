package com.mateuszholik.passwordgenerator.ui.passworddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.PasswordDetails
import com.mateuszholik.domain.usecase.DeletePasswordUseCase
import com.mateuszholik.domain.usecase.GetPasswordUseCase
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
    private val passwordId: Long,
    private val getPasswordUseCase: GetPasswordUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val clipboardManager: ClipboardManager,
    private val validatePasswordUseCase: GetPasswordValidationResultUseCase,
    private val textProvider: TextProvider,
    private val workScheduler: WorkScheduler,
) : BaseViewModel() {

    private val _passwordDeletedSuccessfully = MutableLiveData<Boolean>()
    val passwordDeletedSuccessfully: LiveData<Boolean>
        get() = _passwordDeletedSuccessfully

    private val _passwordDetails = MutableLiveData<PasswordDetails>()
    val passwordDetails: LiveData<PasswordDetails>
        get() = _passwordDetails

    private val _passwordValidationResult = MutableLiveData<List<PasswordValidationResult>>()
    val passwordValidationResult: LiveData<List<PasswordValidationResult>>
        get() = _passwordValidationResult

    init {
        getPassword()
    }

    private fun getPassword() {
        getPasswordUseCase(passwordId)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    _passwordDetails.postValue(it)
                    validatePassword(it.password)
                },
                doOnError = {
                    Timber.e(it, "Error while getting the password")
                    _errorOccurred.value = textProvider.provide(MessageType.GET_PASSWORDS_ERROR)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun validatePassword(password: String) {
        validatePasswordUseCase(password)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    _passwordValidationResult.value = it
                },
                doOnError = {
                    Timber.e(it, "Error while validating the password")
                    _errorOccurred.value = textProvider.provide(MessageType.VALIDATION_ERROR)
                }
            )
            .addTo(compositeDisposable)
    }

    fun copyPasswordToClipboard() =
        clipboardManager.copyToClipboard(
            passwordDetails.value?.platformName.orEmpty(),
            passwordDetails.value?.password.orEmpty()
        )

    fun deletePassword() {
        deletePasswordUseCase(passwordId)
            .andThen(
                Completable.fromAction {
                    workScheduler.cancelWorker(passwordId)
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
