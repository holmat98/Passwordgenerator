package com.mateuszholik.passwordgenerator.ui.loggeduser.passwordscore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszholik.domain.usecase.ValidatePasswordUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class PasswordScoreViewModel(
    private val password: String,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _errorOccurred = MutableLiveData<Boolean>()
    val errorOccurred: LiveData<Boolean>
        get() = _errorOccurred

    val passwordScore = MutableLiveData(50)
    val lengthCondition = MutableLiveData<Boolean>()
    val letterCondition = MutableLiveData<Boolean>()
    val uppercaseCondition = MutableLiveData<Boolean>()
    val numberCondition = MutableLiveData<Boolean>()
    val specialCharacterCondition = MutableLiveData<Boolean>()

    init {
        init()
    }

    private fun init() {
        validatePasswordUseCase(password)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    with(it) {
                        passwordScore.postValue(score)
                        lengthCondition.postValue(hasMinimumLength)
                        letterCondition.postValue(containsLetters)
                        uppercaseCondition.postValue(containsUpperCaseLetters)
                        numberCondition.postValue(containsNumbers)
                        specialCharacterCondition.postValue(containsSpecialCharacters)
                    }
                },
                {
                    _errorOccurred.postValue(true)
                    Log.d(LOG_TAG, "${it.message}")
                }
            )
    }

    private companion object {
        const val LOG_TAG = "PasswordScoreViewModel"
    }
}