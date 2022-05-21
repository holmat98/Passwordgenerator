package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.domain.constants.SharedPrefKeys.SHOULD_USE_BIOMETRIC_AUTH
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable

interface SaveIfShouldUseBiometricAuthenticationUseCase : CompletableUseCase<Boolean>

internal class SaveIfShouldUseBiometricAuthenticationUseCaseImpl(
    private val sharedPrefManager: SharedPrefManager
) : SaveIfShouldUseBiometricAuthenticationUseCase {

    override fun invoke(param: Boolean): Completable =
        Completable.fromAction {
            sharedPrefManager.write(SHOULD_USE_BIOMETRIC_AUTH, param)
        }
}