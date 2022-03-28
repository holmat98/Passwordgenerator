package com.mateuszholik.passwordgenerator.ui.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszholik.domain.usecase.ShouldSkipOnBoardingUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.ui.authentication.models.AuthenticationScreens
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AuthenticationHostViewModel(
    private val shouldSkipOnBoardingUseCase: ShouldSkipOnBoardingUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _currentScreen = MutableLiveData<AuthenticationScreens>()
    val currentScreen: LiveData<AuthenticationScreens>
        get() = _currentScreen

    init {
        displayFirstScreen()
    }

    private fun displayFirstScreen() {
        shouldSkipOnBoardingUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { shouldSkipOnBoarding ->
                    _currentScreen.postValue(
                        if (shouldSkipOnBoarding) {
                            AuthenticationScreens.LOG_IN
                        } else {
                            AuthenticationScreens.ON_BOARDING
                        }
                    )
                },
                {
                    Log.d(LOG_TAG, "${it.message}")
                    _currentScreen.postValue(AuthenticationScreens.ON_BOARDING)
                }
            ).addTo(disposables)
    }

    fun changeScreen(newScreen: AuthenticationScreens) {
        _currentScreen.postValue(newScreen)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    private companion object {
        const val LOG_TAG = "AuthenticationHostViewModel"
    }
}