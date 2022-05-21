package com.mateuszholik.passwordgenerator.factories

import android.app.Activity
import android.hardware.biometrics.BiometricPrompt
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.providers.MessageProvider

interface BiometricPromptFactory {

    fun create(activity: Activity): BiometricPrompt
}

class BiometricPromptFactoryImpl(
    private val messageProvider: MessageProvider
) : BiometricPromptFactory {

    override fun create(activity: Activity) = BiometricPrompt.Builder(activity)
        .setTitle(activity.getString(R.string.biometric_authentication_prompt_title))
        .setNegativeButton(
            activity.getString(R.string.dialog_button_cancel),
            activity.mainExecutor
        ) { _, _ ->
            messageProvider.show(R.string.biometric_authentication_cancelled)
        }
        .build()
}