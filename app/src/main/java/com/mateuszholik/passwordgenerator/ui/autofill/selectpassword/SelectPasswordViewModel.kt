package com.mateuszholik.passwordgenerator.ui.autofill.selectpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails
import com.mateuszholik.domain.usecase.GetAutofillPasswordsDetailsUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class SelectPasswordViewModel(
    private val getAutofillPasswordsDetailsUseCase: GetAutofillPasswordsDetailsUseCase,
) : BaseViewModel() {

    private val _passwords = MutableLiveData<List<AutofillPasswordDetails>>()
    val passwords: LiveData<List<AutofillPasswordDetails>>
        get() = _passwords

    init { getPasswords() }

    private fun getPasswords() {
        getAutofillPasswordsDetailsUseCase()
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { _passwords.postValue(it) },
                doOnError = {
                    Timber.e(it, "Error while getting autofill passwords")
                    _errorOccurred.postValue("Something went wrong")
                }
            ).addTo(compositeDisposable)
    }
}
