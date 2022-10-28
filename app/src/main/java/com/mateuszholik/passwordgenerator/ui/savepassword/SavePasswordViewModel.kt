package com.mateuszholik.passwordgenerator.ui.savepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.passwordgenerator.utils.Constants.EMPTY_STRING
import com.mateuszholik.domain.models.NewPassword
import com.mateuszholik.domain.usecase.GetPasswordUseCase
import com.mateuszholik.domain.usecase.SavePasswordUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.getDiffFromNowInMilliseconds
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.schedulers.WorkScheduler
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import io.reactivex.rxjava3.core.Completable
import timber.log.Timber

class SavePasswordViewModel(
    generatedPassword: String?,
    private val savePasswordUseCase: SavePasswordUseCase,
    private val getPasswordUseCase: GetPasswordUseCase,
    private val workScheduler: WorkScheduler
) : BaseViewModel() {

    private val _savedPassword = MutableLiveData<Int>()
    val savedPassword: LiveData<Int>
        get() = _savedPassword

    val platformName = MutableLiveData(EMPTY_STRING)
    val password = MutableLiveData(EMPTY_STRING)

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
            platformName = platformName.value ?: EMPTY_STRING,
            password = password.value ?: EMPTY_STRING
        )
        savePasswordUseCase(newPassword)
            .flatMap { getPasswordUseCase(it).toSingle() }
            .flatMapCompletable { password ->
                Completable.fromAction {
                    workScheduler.schedule(
                        password.id,
                        password.expiringDate.getDiffFromNowInMilliseconds()
                    )
                }
            }
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { _savedPassword.postValue(R.string.save_password_saved) },
                doOnError = {
                    _errorOccurred.postValue(R.string.save_password_error)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }

    private fun areInputsNotEmpty(): Boolean =
        password.value?.isNotEmpty() ?: false &&
                platformName.value?.isNotEmpty() ?: false
}