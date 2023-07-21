package com.mateuszholik.passwordgenerator.ui.savepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.models.NewPassword
import com.mateuszholik.domain.usecase.GetPasswordUseCase
import com.mateuszholik.domain.usecase.InsertPasswordAndGetIdUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.expirationDate
import com.mateuszholik.passwordgenerator.extensions.getDiffFromNowInMilliseconds
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.schedulers.WorkScheduler
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import com.mateuszholik.passwordgenerator.utils.Constants.EMPTY_STRING
import io.reactivex.rxjava3.core.Completable
import timber.log.Timber

class SavePasswordViewModel(
    generatedPassword: String?,
    private val insertPasswordAndGetIdUseCase: InsertPasswordAndGetIdUseCase,
    private val getPasswordUseCase: GetPasswordUseCase,
    private val workScheduler: WorkScheduler,
    private val textProvider: TextProvider,
) : BaseViewModel() {

    private val _savePasswordSuccess = MutableLiveData<String>()
    val savePasswordSuccess: LiveData<String>
        get() = _savePasswordSuccess

    val platformName = MutableLiveData(EMPTY_STRING)
    val password = MutableLiveData(EMPTY_STRING)
    val newWebsiteValue = MutableLiveData(EMPTY_STRING)
    val isExpiring = MutableLiveData(true)

    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        value = false
        addSource(platformName) {
            value = areInputsNotEmpty()
        }
        addSource(password) {
            value = areInputsNotEmpty()
        }
    }

    init {
        password.postValue(generatedPassword)
    }

    fun savePassword() {
        val newPassword = NewPassword(
            platformName = platformName.value.orEmpty(),
            password = password.value.orEmpty(),
            website = newWebsiteValue.value.orEmpty(),
            isExpiring = isExpiring.value ?: false
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
                doOnSuccess = { _savePasswordSuccess.postValue(textProvider.provide(MessageType.SAVE_PASSWORD_SUCCESS)) },
                doOnError = {
                    _errorOccurred.postValue(textProvider.provide(MessageType.SAVE_PASSWORD_ERROR))
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun areInputsNotEmpty(): Boolean =
        password.value?.isNotEmpty() ?: false &&
                platformName.value?.isNotEmpty() ?: false
}
