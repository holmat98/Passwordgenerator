package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.OldPasswordsRepository
import com.mateuszholik.domain.mappers.UpdatedPasswordMapper
import com.mateuszholik.domain.models.UpdatedPassword
import com.mateuszholik.domain.usecase.base.CompletableParameterizedUseCase
import io.reactivex.rxjava3.core.Completable

interface UpdatePasswordUseCase : CompletableParameterizedUseCase<UpdatedPassword>

internal class UpdatePasswordUseCaseImpl(
    private val oldPasswordsRepository: OldPasswordsRepository,
    private val updatedPasswordMapper: UpdatedPasswordMapper
) : UpdatePasswordUseCase {

    override fun invoke(param: UpdatedPassword): Completable =
        oldPasswordsRepository.update(updatedPasswordMapper.map(param))
}
