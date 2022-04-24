package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.models.NewPassword
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable

interface SavePasswordUseCase : CompletableUseCase<NewPassword>

internal class SavePasswordUseCaseImpl(
    private val passwordsRepository: PasswordsRepository
) : SavePasswordUseCase {

    override fun invoke(param: NewPassword): Completable =
        passwordsRepository.insert(param.platformName, param.password)
}