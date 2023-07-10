package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.domain.usecase.base.ParameterizedMaybeUseCase
import io.reactivex.rxjava3.core.Maybe

interface GetPasswordUseCase : ParameterizedMaybeUseCase<Long, PasswordInfo>

internal class GetPasswordUseCaseImpl(
    private val passwordsRepository: PasswordsRepository
) : GetPasswordUseCase {

    override fun invoke(param: Long): Maybe<PasswordInfo> =
        passwordsRepository.getPassword(param)
}
