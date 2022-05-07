package com.mateuszholik.passwordgenerator.ui.loggeduser.passwordscore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszholik.domain.usecase.CalculatePasswordScoreUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class PasswordScoreViewModel(
    private val password: String,
    private val calculatePasswordScoreUseCase: CalculatePasswordScoreUseCase
) : ViewModel() {

    private val _errorOccurred = MutableLiveData<Boolean>()
    val errorOccurred: LiveData<Boolean>
        get() = _errorOccurred

    val passwordScore = MutableLiveData<Int>()

    init {
        calculatePasswordScore()
    }

    private fun calculatePasswordScore() {
        calculatePasswordScoreUseCase(password)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { passwordScore.postValue(it) },
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