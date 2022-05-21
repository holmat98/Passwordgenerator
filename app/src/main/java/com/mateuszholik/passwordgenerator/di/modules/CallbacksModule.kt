package com.mateuszholik.passwordgenerator.di.modules

import com.mateuszholik.passwordgenerator.callbacks.BiometricAuthenticationCallback
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import org.koin.core.qualifier.named
import org.koin.dsl.module

val callbacksModule = module {

    factory { (doOnSuccess: () -> Unit) ->
        BiometricAuthenticationCallback(
            messageProvider = get(named(TOAST_MESSAGE_PROVIDER)),
            doOnSuccess = doOnSuccess
        )
    }
}