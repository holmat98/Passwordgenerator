package com.mateuszholik.passwordgenerator.ui.loggeduser.passwordvalidationresult

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszholik.domain.usecase.ValidatePasswordUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class PasswordValidationResultViewModel(
    private val password: String,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    val lengthCondition = MutableLiveData<Boolean>()
    val letterCondition = MutableLiveData<Boolean>()
    val uppercaseCondition = MutableLiveData<Boolean>()
    val numberCondition = MutableLiveData<Boolean>()
    val specialCharacterCondition = MutableLiveData<Boolean>()

    init {
        validatePassword()
    }

    private fun validatePassword() {
        validatePasswordUseCase(password)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    with(it) {
                        lengthCondition.postValue(hasMinimumLength)
                        letterCondition.postValue(containsLetters)
                        uppercaseCondition.postValue(containsUpperCaseLetters)
                        numberCondition.postValue(containsNumbers)
                        specialCharacterCondition.postValue(containsSpecialCharacters)
                    }
                },
                {
                    Log.d("TEST", "${it.message}")
                }
            )
    }
}