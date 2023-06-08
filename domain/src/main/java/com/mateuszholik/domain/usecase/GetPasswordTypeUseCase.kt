package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.mappers.PasswordToPasswordTypeMapper
import com.mateuszholik.domain.models.PasswordInfo
import com.mateuszholik.domain.models.PasswordType
import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import io.reactivex.rxjava3.core.Single

interface GetPasswordTypeUseCase : ParameterizedSingleUseCase<Long, PasswordType>

internal class GetPasswordTypeUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
    private val passwordToPasswordTypeMapper: PasswordToPasswordTypeMapper,
    private val getPasswordValidationResultUseCase: GetPasswordValidationResultUseCase,
) : GetPasswordTypeUseCase {

    override fun invoke(param: Long): Single<PasswordType> =
        passwordsRepository.getPassword(param)
            .toSingle()
            .flatMap { password ->
                getPasswordValidationResultUseCase(password.password)
                    .map { validationResult ->
                        val score = validationResult.sumOf { it.score }
                        val maxScore = validationResult.sumOf { it.maxScore }

                        val scorePct = ((score.toFloat() / maxScore.toFloat()) * 100).toInt()

                        passwordToPasswordTypeMapper.map(PasswordInfo(password, scorePct))
                    }
            }
}
