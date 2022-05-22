package com.mateuszholik.passwordgenerator.ui.loggeduser.passwordvalidationresult

import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.ValidatePasswordUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class PasswordValidationResultViewModel(
    private val password: String,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : BaseViewModel() {

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
            .subscribeWithObserveOnMainThread(
                scheduler = Schedulers.computation(),
                doOnSuccess = {
                    with(it) {
                        lengthCondition.postValue(hasMinimumLength)
                        letterCondition.postValue(containsLetters)
                        uppercaseCondition.postValue(containsUpperCaseLetters)
                        numberCondition.postValue(containsNumbers)
                        specialCharacterCondition.postValue(containsSpecialCharacters)
                    }
                },
                doOnError = {
                    _errorOccurred.postValue(R.string.password_validation_error)
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }
}