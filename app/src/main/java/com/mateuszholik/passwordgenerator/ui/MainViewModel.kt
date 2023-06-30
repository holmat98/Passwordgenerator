package com.mateuszholik.passwordgenerator.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _shouldLogOut = MutableLiveData<Boolean>()
    val shouldLogOut: LiveData<Boolean>
        get() = _shouldLogOut

    private var timeOfGoingToBackground: Long? =  null

    fun onGoToBackground() {
        timeOfGoingToBackground = System.currentTimeMillis()
    }

    fun onBackFromBackground() {
        timeOfGoingToBackground?.let {
            val timeOfBeingInBackground = System.currentTimeMillis() - it
            if (timeOfBeingInBackground > TIMEOUT_IN_MILLISECONDS) {
                _shouldLogOut.value = true
            }
        }
    }

    private companion object {
        const val TIMEOUT_IN_MILLISECONDS = 120_000L
    }
}
