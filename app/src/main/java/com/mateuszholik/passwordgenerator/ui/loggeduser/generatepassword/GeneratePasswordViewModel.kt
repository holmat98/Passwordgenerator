package com.mateuszholik.passwordgenerator.ui.loggeduser.generatepassword

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszholik.domain.constants.Constants.EMPTY_STRING
import com.mateuszholik.domain.usecase.CreatePasswordUseCase
import com.mateuszholik.passwordgenerator.listeners.OnValueChangedListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class GeneratePasswordViewModel(
    private val createPasswordUseCase: CreatePasswordUseCase
) : ViewModel(), OnValueChangedListener {

    private val _generatedPassword = MutableLiveData(EMPTY_STRING)
    val generatedPassword: LiveData<String>
        get() = _generatedPassword

    private var passwordLength: Int = 8

    fun createPassword() {
        createPasswordUseCase(passwordLength)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _generatedPassword.postValue(it)
                },
                {
                    Log.d(LOG_TAG, "${it.message}")
                }
            )
    }

    override fun onValueChanged(value: Float) {
        passwordLength = value.toInt()
    }

    private companion object {
        const val LOG_TAG = "GeneratePasswordViewModel"
    }
}