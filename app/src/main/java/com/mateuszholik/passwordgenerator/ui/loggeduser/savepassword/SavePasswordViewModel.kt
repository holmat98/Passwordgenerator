package com.mateuszholik.passwordgenerator.ui.loggeduser.savepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.constants.Constants.EMPTY_STRING
import com.mateuszholik.domain.models.NewPassword
import com.mateuszholik.domain.usecase.SavePasswordUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class SavePasswordViewModel(
    generatedPassword: String?,
    private val savePasswordUseCase: SavePasswordUseCase
) : BaseViewModel() {

    private val _savedPassword = MutableLiveData<Boolean>()
    val savedPassword: LiveData<Boolean>
        get() = _savedPassword

    val platformName = MutableLiveData(EMPTY_STRING)
    val password = MutableLiveData(EMPTY_STRING)

    init {
        password.postValue(generatedPassword)
    }

    fun savePassword() {
        val newPassword = NewPassword(
            platformName = platformName.value ?: EMPTY_STRING,
            password = password.value ?: EMPTY_STRING
        )
        savePasswordUseCase(newPassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _savedPassword.postValue(true) },
                {
                    _errorOccurred.postValue(true)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }
}