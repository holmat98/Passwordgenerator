package com.mateuszholik.passwordgenerator.ui.passworddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.models.PasswordType
import com.mateuszholik.domain.usecase.DeletePasswordUseCase
import com.mateuszholik.domain.usecase.GetPasswordTypeUseCase
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
    private val getPasswordTypeUseCase: GetPasswordTypeUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val clipboardManager: ClipboardManager,
    private val validatePasswordUseCase: GetPasswordValidationResultUseCase,
    private val textProvider: TextProvider,
    private val workScheduler: WorkScheduler,
) : BaseViewModel() {

    private val _passwordDeletedSuccessfully = MutableLiveData<Boolean>()
    val passwordDeletedSuccessfully: LiveData<Boolean>
        get() = _passwordDeletedSuccessfully

    private val _passwordType = MutableLiveData<PasswordType>()
    val passwordType: LiveData<PasswordType>
        get() = _passwordType

    private val _passwordValidationResult = MutableLiveData<List<PasswordValidationResult>>()
    val passwordValidationResult: LiveData<List<PasswordValidationResult>>
        get() = _passwordValidationResult

    init {
        getPassword()
    }

    private fun getPassword() {
        getPasswordTypeUseCase(passwordId)
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    _passwordType.value = it
                    validatePassword(it.password)
                },
                doOnError = {
                    Timber.e(it, "Error while getting the password")
                    _errorOccurred.value = textProvider.provide(MessageType.GET_PASSWORDS_ERROR)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun validatePassword(password: Password) {
        validatePasswordUseCase(password.password)
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
            passwordType.value?.password?.platformName.orEmpty(),
            passwordType.value?.password?.password.orEmpty()
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
