package com.mateuszholik.passwordgenerator.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.ShouldSkipOnBoardingUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.authentication.models.AuthenticationScreens
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import timber.log.Timber

class AuthenticationHostViewModel(
    private val shouldSkipOnBoardingUseCase: ShouldSkipOnBoardingUseCase
) : BaseViewModel() {

    private val _currentScreen = MutableLiveData<AuthenticationScreens>()
    val currentScreen: LiveData<AuthenticationScreens>
        get() = _currentScreen

    init {
        displayFirstScreen()
    }

    private fun displayFirstScreen() {
        shouldSkipOnBoardingUseCase()
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

    fun changeScreen(newScreen: AuthenticationScreens) {
        _currentScreen.postValue(newScreen)
    }
}