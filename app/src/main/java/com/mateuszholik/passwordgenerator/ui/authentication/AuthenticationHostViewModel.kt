package com.mateuszholik.passwordgenerator.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.IsPinCreatedUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.authentication.models.AuthenticationScreens
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class AuthenticationHostViewModel(
    private val isPinCreatedUseCase: IsPinCreatedUseCase
) : BaseViewModel() {

    private val _currentScreen = MutableLiveData<AuthenticationScreens>()
    val currentScreen: LiveData<AuthenticationScreens>
        get() = _currentScreen

    private val _isProgressBarVisible = MutableLiveData(true)
    val isProgressBarVisible: LiveData<Boolean>
        get() = _isProgressBarVisible

    init {
        displayFirstScreen()
    }

    private fun displayFirstScreen() {
        isPinCreatedUseCase()
            .doOnSubscribe { _isProgressBarVisible.postValue(true) }
            .doFinally { _isProgressBarVisible.postValue(false) }
            .subscribeWithObserveOnMainThread(
                doOnSuccess = { isPinCreated ->
                    _currentScreen.postValue(
                        if (isPinCreated) {
                            AuthenticationScreens.LOG_IN
                        } else {
                            AuthenticationScreens.CREATE_PIN
                        }
                    )
                },
                doOnError = {
                    Timber.e(it)
                    _currentScreen.postValue(AuthenticationScreens.CREATE_PIN)
                }
            )
            .addTo(compositeDisposable)
    }
}