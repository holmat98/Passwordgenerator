package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable

interface UpdatePasswordUseCase : CompletableUseCase<Password>

internal class UpdatePasswordUseCaseImpl(
    private val passwordsRepository: PasswordsRepository
) : UpdatePasswordUseCase {

    override fun invoke(param: Password): Completable =
        passwordsRepository.update(param)
}