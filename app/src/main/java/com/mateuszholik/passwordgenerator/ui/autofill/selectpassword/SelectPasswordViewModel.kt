package com.mateuszholik.passwordgenerator.ui.autofill.selectpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails
import com.mateuszholik.domain.models.NewPackage
import com.mateuszholik.domain.usecase.GetAutofillPasswordsDetailsUseCase
import com.mateuszholik.domain.usecase.UpdatePackageNameUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.models.MessageType
import com.mateuszholik.passwordgenerator.providers.TextProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class SelectPasswordViewModel(
    private val getAutofillPasswordsDetailsUseCase: GetAutofillPasswordsDetailsUseCase,
    private val updatePackageNameUseCase: UpdatePackageNameUseCase,
    private val textProvider: TextProvider,
) : BaseViewModel() {

    private val _passwords = MutableLiveData<List<AutofillPasswordDetails>>()
    val passwords: LiveData<List<AutofillPasswordDetails>>
        get() = _passwords

    private val _packageNameUpdateCompleted = MutableLiveData<Boolean>(false)
    val packageNameUpdateCompleted: LiveData<Boolean>
        get() = _packageNameUpdateCompleted

    fun getPasswords(packageName: String?) {
        getAutofillPasswordsDetailsUseCase()
            .map {
                val autofillPasswords = mutableSetOf<AutofillPasswordDetails>()
                packageName?.let { packageName ->
                    val matchingPackages = it.filter { autofillPassword ->
                        autofillPassword.packageName == packageName
                    }
                    autofillPasswords.addAll(matchingPackages)
                }
                autofillPasswords.addAll(it)

                autofillPasswords.toList()
            }
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { _passwords.postValue(it) },
                doOnError = {
                    Timber.e(it, "Error while getting autofill passwords")
                    _errorOccurred.postValue(textProvider.provide(MessageType.GET_AUTOFILL_PASSWORD_ERROR))
                }
            ).addTo(compositeDisposable)
    }

    fun updatePackageName(passwordId: Long, packageName: String) {
        updatePackageNameUseCase(
            NewPackage(
                passwordId = passwordId,
                packageName = packageName
            )
        ).subscribeWithObserveOnMainThread(
            doOnSuccess = { _packageNameUpdateCompleted.postValue(true) },
            doOnError = {
                Timber.e(it, "Error while updating the package name")
                _packageNameUpdateCompleted.postValue(true)
            }
        ).addTo(compositeDisposable)
    }
}
