package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.mappers.UpdatedPasswordMapper
import com.mateuszholik.domain.models.UpdatedPassword
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable

interface UpdatePasswordUseCase : CompletableUseCase<UpdatedPassword>

internal class UpdatePasswordUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
    private val updatedPasswordMapper: UpdatedPasswordMapper
) : UpdatePasswordUseCase {

    override fun invoke(param: UpdatedPassword): Completable =
        passwordsRepository.update(updatedPasswordMapper.map(param))
}