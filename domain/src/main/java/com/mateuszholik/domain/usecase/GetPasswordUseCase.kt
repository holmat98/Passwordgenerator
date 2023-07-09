package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.OldPasswordsRepository
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.usecase.base.ParameterizedMaybeUseCase
import io.reactivex.rxjava3.core.Maybe

interface GetPasswordUseCase : ParameterizedMaybeUseCase<Long, Password>

internal class GetPasswordUseCaseImpl(
    private val oldPasswordsRepository: OldPasswordsRepository
) : GetPasswordUseCase {

    override fun invoke(param: Long): Maybe<Password> =
        oldPasswordsRepository.getPassword(param)
}
