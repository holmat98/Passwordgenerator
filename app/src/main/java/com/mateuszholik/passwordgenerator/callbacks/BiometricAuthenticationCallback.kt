package com.mateuszholik.passwordgenerator.callbacks

import android.hardware.biometrics.BiometricPrompt
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.providers.MessageProvider

class BiometricAuthenticationCallback(
    private val messageProvider: MessageProvider,
    private val doOnSuccess: () -> Unit
) : BiometricPrompt.AuthenticationCallback() {

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        super.onAuthenticationError(errorCode, errString)

        messageProvider.show(R.string.biometric_authentication_error)
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()

        messageProvider.show(R.string.biometric_authentication_failed)
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)

        doOnSuccess()
    }
}