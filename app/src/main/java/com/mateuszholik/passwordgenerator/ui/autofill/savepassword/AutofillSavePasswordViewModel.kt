package com.mateuszholik.passwordgenerator.ui.autofill.savepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.models.NewPassword
import com.mateuszholik.domain.usecase.GetPasswordUseCase
import com.mateuszholik.domain.usecase.InsertPasswordAndGetIdUseCase
import com.mateuszholik.passwordgenerator.extensions.addSources
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.expirationDate
import com.mateuszholik.passwordgenerator.extensions.getDiffFromNowInMilliseconds
import com.mateuszholik.passwordgenerator.extensions.orFalse
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.schedulers.WorkScheduler
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordgenerator.utils.Constants.EMPTY_STRING
import io.reactivex.rxjava3.core.Completable
import timber.log.Timber

class AutofillSavePasswordViewModel(
    private val insertPasswordAndGetIdUseCase: InsertPasswordAndGetIdUseCase,
    private val getPasswordUseCase: GetPasswordUseCase,
    private val workScheduler: WorkScheduler,
    private val textProvider: TextProvider,
    private val packageName: String?,
) : BaseViewModel() {

    private val _savePasswordSuccess = MutableLiveData<AutofillResult>()
    val savePasswordSuccess: LiveData<AutofillResult>
        get() = _savePasswordSuccess

    val platformName = MutableLiveData(EMPTY_STRING)
    val password = MutableLiveData(EMPTY_STRING)
    val isExpiring = MutableLiveData(true)
    val shouldShowSavePackageNameOption = MutableLiveData(!packageName.isNullOrEmpty())
    val shouldSavePackageName = MutableLiveData(false)

    val isButtonEnabled = MediatorLiveData<Boolean>().addSources(
        platformName,
        password
    ) { areInputsNotEmpty() }

    fun savePassword() {
        val newPassword = NewPassword(
            platformName = platformName.value.orEmpty(),
            password = password.value.orEmpty(),
            website = null,
            isExpiring = isExpiring.value.orFalse(),
            packageName = packageName.takeIf { shouldSavePackageName.value.orFalse() }
        )
        val result = AutofillResult(
            promptMessage = newPassword.platformName,
            autofillValue = newPassword.password
        )
        insertPasswordAndGetIdUseCase(newPassword)
            .flatMap { getPasswordUseCase(it).toSingle() }
            .flatMapCompletable { password ->
                Completable.fromAction {
                    password.passwordValidity.expirationDate?.let {
                        workScheduler.schedule(
                            password.id,
                            it.getDiffFromNowInMilliseconds()
                        )
                    }
                }
            }
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { _savePasswordSuccess.postValue(result) },
                doOnError = {
                    _errorOccurred.postValue(textProvider.provide(MessageType.SAVE_PASSWORD_ERROR))
                    Timber.e(it, "Error while saving password in autofill activity")
                }
            )
            .addTo(compositeDisposable)
    }

    private fun areInputsNotEmpty(): Boolean =
        password.value?.isNotEmpty().orFalse() &&
                platformName.value?.isNotEmpty().orFalse()

    data class AutofillResult(
        val promptMessage: String,
        val autofillValue: String,
    )
}
