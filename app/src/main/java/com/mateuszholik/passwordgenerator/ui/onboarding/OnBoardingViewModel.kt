package com.mateuszholik.passwordgenerator.ui.onboarding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszholik.domain.usecase.ShouldSkipOnBoardingUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class OnBoardingViewModel(
    private val shouldSkipOnBoardingUseCase: ShouldSkipOnBoardingUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _shouldGoToNextScreen = MutableLiveData<Boolean>()
    val shouldGoToNextScreen: LiveData<Boolean>
        get() = _shouldGoToNextScreen

    init {
        getInfoAboutChangingScreen()
    }

    private fun getInfoAboutChangingScreen() {
        shouldSkipOnBoardingUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _shouldGoToNextScreen.postValue(it) },
                {
                    _shouldGoToNextScreen.postValue(false)
                    Log.d(LOG_TAG, "${it.message}")
                }
            )
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    companion object {
        private const val LOG_TAG = "OnBoardingViewModel"
    }
}