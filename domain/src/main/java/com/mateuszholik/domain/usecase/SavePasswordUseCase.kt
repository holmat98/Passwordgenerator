package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.models.NewPassword
import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import io.reactivex.rxjava3.core.Single

interface SavePasswordUseCase : ParameterizedSingleUseCase<NewPassword, Long>

internal class SavePasswordUseCaseImpl(
    private val passwordsRepository: PasswordsRepository
) : SavePasswordUseCase {

    override fun invoke(param: NewPassword): Single<Long> =
        passwordsRepository.insert(param.platformName, param.password)
}