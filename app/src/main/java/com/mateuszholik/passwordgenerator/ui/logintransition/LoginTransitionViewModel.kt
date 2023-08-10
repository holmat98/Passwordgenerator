package com.mateuszholik.passwordgenerator.ui.logintransition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.IsPinCreatedUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class LoginTransitionViewModel(
    private val isPinCreatedUseCase: IsPinCreatedUseCase
) : BaseViewModel() {

    private val _isPinCreated = MutableLiveData<Boolean>()
    val isPinCreated: LiveData<Boolean>
        get() = _isPinCreated

    init {
        getIsPinCreated()
    }

    private fun getIsPinCreated() {
        isPinCreatedUseCase()
            .subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    _isPinCreated.postValue(it)
                },
                doOnError = {
                    _isPinCreated.postValue(false)
                    Timber.i("Cannot check if pin is created", it)
                }
            )
            .addTo(compositeDisposable)
    }
}
