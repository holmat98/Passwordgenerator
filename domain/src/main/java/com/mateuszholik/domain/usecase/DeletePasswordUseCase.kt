package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.OldPasswordsRepository
import com.mateuszholik.domain.usecase.base.CompletableParameterizedUseCase
import io.reactivex.rxjava3.core.Completable

interface DeletePasswordUseCase : CompletableParameterizedUseCase<Long>

internal class DeletePasswordUseCaseImpl(
    private val oldPasswordsRepository: OldPasswordsRepository
) : DeletePasswordUseCase {

    override fun invoke(param: Long): Completable = oldPasswordsRepository.delete(param)
}
