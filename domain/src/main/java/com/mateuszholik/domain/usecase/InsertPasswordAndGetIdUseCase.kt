package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.mappers.NewPasswordMapper
import com.mateuszholik.domain.models.NewPassword
import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import io.reactivex.rxjava3.core.Single

interface InsertPasswordAndGetIdUseCase : ParameterizedSingleUseCase<NewPassword, Long>

internal class InsertPasswordAndGetIdUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
    private val newPasswordMapper: NewPasswordMapper,
    private val getPasswordScoreUseCase: GetPasswordScoreUseCase,
) : InsertPasswordAndGetIdUseCase {

    override fun invoke(param: NewPassword): Single<Long> =
        getPasswordScoreUseCase(param.password)
            .flatMap { passwordScore ->
                passwordsRepository.insertAndGetId(
                    newPasswordMapper.map(
                        NewPasswordMapper.Param(
                            platformName = param.platformName,
                            password = param.password,
                            website = param.website,
                            isExpiring = param.isExpiring,
                            passwordScore = passwordScore,
                            packageName = param.packageName
                        )
                    )
                )
            }
}
