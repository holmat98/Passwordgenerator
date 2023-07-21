package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.data.managers.io.SharedPrefKeys.SHOULD_USE_BIOMETRIC_AUTH
import com.mateuszholik.domain.usecase.base.CompletableParameterizedUseCase
import io.reactivex.rxjava3.core.Completable

interface SaveIfShouldUseBiometricAuthenticationUseCase : CompletableParameterizedUseCase<Boolean>

internal class SaveIfShouldUseBiometricAuthenticationUseCaseImpl(
    private val sharedPrefManager: SharedPrefManager
) : SaveIfShouldUseBiometricAuthenticationUseCase {

    override fun invoke(param: Boolean): Completable =
        Completable.fromAction {
            sharedPrefManager.write(SHOULD_USE_BIOMETRIC_AUTH, param)
        }
}
