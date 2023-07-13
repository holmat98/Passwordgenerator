package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.mappers.UpdatedPasswordMapper
import com.mateuszholik.domain.models.UpdatedPassword
import com.mateuszholik.domain.usecase.base.CompletableParameterizedUseCase
import io.reactivex.rxjava3.core.Completable

interface UpdatePasswordUseCase : CompletableParameterizedUseCase<UpdatedPassword>

internal class UpdatePasswordUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
    private val updatedPasswordMapper: UpdatedPasswordMapper,
    private val getPasswordScoreUseCase: GetPasswordScoreUseCase,
) : UpdatePasswordUseCase {

    override fun invoke(param: UpdatedPassword): Completable =
        getPasswordScoreUseCase(param.password)
            .flatMapCompletable { passwordScore ->
                passwordsRepository.update(
                    updatedPasswordMapper.map(
                        UpdatedPasswordMapper.Param(
                            id = param.id,
                            password = param.password,
                            platformName = param.platformName,
                            website = param.website,
                            isExpiring = param.isExpiring,
                            passwordScore = passwordScore
                        )
                    )
                )
            }
}
