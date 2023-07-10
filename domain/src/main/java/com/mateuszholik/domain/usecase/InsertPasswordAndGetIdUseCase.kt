package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.mappers.NewPasswordMapper
import com.mateuszholik.domain.models.NewPassword
import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import io.reactivex.rxjava3.core.Single

interface InsertPasswordAndGetIdUseCase : ParameterizedSingleUseCase<NewPassword, Long>

internal class InsertPasswordAndGetIdUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
    private val newPasswordMapper: NewPasswordMapper
) : InsertPasswordAndGetIdUseCase {

    override fun invoke(param: NewPassword): Single<Long> =
        passwordsRepository.insertAndGetId(newPasswordMapper.map(param))
}
