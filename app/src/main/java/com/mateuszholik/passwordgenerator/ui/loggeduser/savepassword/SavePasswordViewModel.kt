package com.mateuszholik.passwordgenerator.ui.loggeduser.savepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszholik.domain.constants.Constants.EMPTY_STRING
import com.mateuszholik.domain.models.NewPassword
import com.mateuszholik.domain.usecase.SavePasswordUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SavePasswordViewModel(
    generatedPassword: String?,
    private val savePasswordUseCase: SavePasswordUseCase
) : ViewModel() {

    private val _savedPassword = MutableLiveData<Boolean>()
    val savedPassword: LiveData<Boolean>
        get() = _savedPassword

    private val _errorOccurred = MutableLiveData<Boolean>()
    val errorOccurred: LiveData<Boolean>
        get() = _errorOccurred

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
                { _errorOccurred.postValue(true) }
            )
    }
}