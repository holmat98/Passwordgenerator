package com.mateuszholik.domain.usecase

import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.domain.constants.SharedPrefKeys.SHOULD_USE_BIOMETRIC_AUTH
import com.mateuszholik.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Single

interface ShouldUseBiometricAuthenticationUseCase : UseCase<Boolean>

internal class ShouldUseBiometricAuthenticationUseCaseImpl(
    private val sharedPrefManager: SharedPrefManager
) : ShouldUseBiometricAuthenticationUseCase {

    override fun invoke(): Single<Boolean> =
        Single.just(
            sharedPrefManager.readBoolean(SHOULD_USE_BIOMETRIC_AUTH)
        )
}