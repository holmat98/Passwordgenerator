package com.mateuszholik.passwordgenerator.factories

import android.annotation.SuppressLint
import android.app.Activity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import com.mateuszholik.passwordgenerator.R

interface BiometricPromptFactory {

    fun create(activity: Activity): BiometricPrompt.PromptInfo
}

class BiometricPromptFactoryImpl : BiometricPromptFactory {

    @SuppressLint("WrongConstant")
    override fun create(activity: Activity) = BiometricPrompt.PromptInfo.Builder()
        .setTitle(activity.getString(R.string.biometric_authentication_prompt_title))
        .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        .build()
}