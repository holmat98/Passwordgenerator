package com.mateuszholik.passwordgenerator.managers

import android.annotation.SuppressLint
import android.app.Activity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.mateuszholik.passwordgenerator.callbacks.BiometricAuthenticationCallback
import com.mateuszholik.passwordgenerator.factories.BiometricPromptFactory

class BiometricManager(private val biometricPromptFactory: BiometricPromptFactory) {

    @SuppressLint("WrongConstant")
    fun isBiometricAvailable(activity: Activity): Boolean {
        val biometricManager = BiometricManager.from(activity)

        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun showBiometricPrompt(
        activity: FragmentActivity,
        biometricAuthenticationCallback: BiometricAuthenticationCallback
    ) {
        val biometricPromptInfo = biometricPromptFactory.create(activity)

        val biometricPrompt = BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(activity),
            biometricAuthenticationCallback
        )

        biometricPrompt.authenticate(biometricPromptInfo)
    }
}