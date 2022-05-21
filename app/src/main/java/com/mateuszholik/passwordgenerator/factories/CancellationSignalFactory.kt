package com.mateuszholik.passwordgenerator.factories

import android.os.CancellationSignal
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.providers.MessageProvider

interface CancellationSignalFactory {

    fun create(): CancellationSignal
}

class CancellationSignalFactoryImpl(
    private val messageProvider: MessageProvider
) : CancellationSignalFactory {

    override fun create() = CancellationSignal().apply {
        setOnCancelListener {
            messageProvider.show(R.string.biometric_authentication_cancelled)
        }
    }
}